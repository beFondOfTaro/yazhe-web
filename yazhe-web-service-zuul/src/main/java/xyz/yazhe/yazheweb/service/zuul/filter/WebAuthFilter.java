package xyz.yazhe.yazheweb.service.zuul.filter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.yazhe.yazheweb.service.domain.common.constants.CommonConstants;
import xyz.yazhe.yazheweb.service.domain.common.constants.HttpParamKey;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;
import xyz.yazhe.yazheweb.service.domain.exception.CommonException;
import xyz.yazhe.yazheweb.service.domain.user.auth.DO.Permission;
import xyz.yazhe.yazheweb.service.util.GsonUtil;
import xyz.yazhe.yazheweb.service.util.JWTUtil;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;
import xyz.yazhe.yazheweb.service.zuul.feign.UserFeign;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 身份认证过滤器
 * @author Yazhe
 * Created at 16:36 2018/8/27
 */
@Component
public class WebAuthFilter extends ZuulFilter{

	private static final Logger logger = LoggerFactory.getLogger(WebAuthFilter.class);

	/**
	 * url表达式的分隔符
	 */
	private static final String URL_EXPRESSION_SEPARATOR = ",";
	/**
	 * path variable占位符
	 */
	private static final String PLACEHOLDER = "/*";

	/**
	 * 不进行过滤的url
	 */
	@Value("${filter.web-auth.ignored-mappings}")
	private String ignoredMappings;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Autowired
	private UserFeign userFeign;
	private Gson gson = GsonUtil.getGson();

	@Override
	public Object run() {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		HttpServletResponse response = requestContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		//判断不进行过滤的url
		String requestUrl = request.getRequestURI();
		String requestMethod = request.getMethod().toLowerCase();
		if (matchUrl(requestUrl, requestMethod, ignoredMappings)){
			return null;
		}
		//获取token
		String token = request.getHeader(HttpParamKey.TOKEN);
		String userId;
		try {
			//校验token
			if (StringUtils.isEmpty(token)){
				throw new CommonException(ResultEnum.INVALID_TOKEN);
			}
			userId = JWTUtil.getUserId(token);
			if (userId == null || !token.equals(redisTemplate.boundValueOps(CommonConstants.RedisKey.AUTH_TOKEN_PREFIX + userId).get())){
				//返回错误
				throw new CommonException(ResultEnum.TOKEN_EXPIRED);
			}
		}
		catch (CommonException e){
			logger.error(e.getMessage());
			requestContext.setResponseBody(gson.toJson(ResultVOUtil.error(e.getCode(),e.getMessage())));
			requestContext.setResponseStatusCode(401);
			requestContext.setSendZuulResponse(false);
			return null;
		}
		//校验用户权限
		if (!checkPermission(userId,requestUrl,requestMethod)){
			requestContext.setResponseBody(gson.toJson(ResultVOUtil.error(ResultEnum.UNAUTHORIZED)));
			requestContext.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
			requestContext.setSendZuulResponse(false);
		}
		return null;
	}

	/**
	 * 匹配url，支持sourceUrl结尾为path variable占位符的形式，如test/*可以匹配请求url为test/1
	 * @param requestUrl 请求的url
	 * @param requestMethod 请求的方法
	 * @param urlExpression 源url，即用户权限表中的url
	 * @return
	 */
	private boolean matchUrl(String requestUrl, String requestMethod, String urlExpression) {
		for (String sourceUrl : urlExpression.split(URL_EXPRESSION_SEPARATOR)) {
			String url;
			String mappedMethods;
			int leftIdx = sourceUrl.indexOf("[");
			int rightIdx = sourceUrl.indexOf("]");
			//如果存在[]，则需要匹配method
			if (leftIdx > -1 && rightIdx > -1){
				url = sourceUrl.substring(0,leftIdx);
				mappedMethods = sourceUrl.substring(leftIdx+1,rightIdx).toLowerCase();
				if (url.contains(requestUrl) && mappedMethods.contains(requestMethod)){
					return true;
				}
			}else {
				//不存在[]，直接匹配url
				if (sourceUrl.endsWith(PLACEHOLDER)) {
					//匹配占位符之前的url是否相同
					if (sourceUrl.substring(0, sourceUrl.lastIndexOf(PLACEHOLDER)).contains(
							requestUrl.substring(0, requestUrl.lastIndexOf("/")))) {
						return true;
					}
				} else if (sourceUrl.contains(requestUrl)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 校验用户权限
	 * @param userId
	 * @param requestUrl
	 * @param requestMethod
	 * @return
	 */
	private boolean checkPermission(String userId,String requestUrl,String requestMethod){
		String userTokenKey = CommonConstants.RedisKey.USER_PERMISSION_PREFIX + userId;
		//先查询缓存
		if (redisTemplate.hasKey(userTokenKey)){
			BoundValueOperations<String,String> boundListOperations = redisTemplate.boundValueOps(userTokenKey);
			boundListOperations.expire(CommonConstants.REDIS_KEY_DEFAULT_EXPIRE_TIME,TimeUnit.SECONDS);
			List<Permission> permissionList = gson.fromJson(boundListOperations.get(),new TypeToken<List<Permission>>(){}.getType());
			for (Object permission : permissionList) {
				if (matchUrl(requestUrl,requestMethod,((Permission)permission).getUrl())) {
					return true;
				}
			}
		}else{
			//缓存没有那么查询数据库
			JsonArray permissionList = gson.toJsonTree(userFeign.listPermission().getData()).getAsJsonArray();
			//缓存到redis
			redisTemplate.boundValueOps(userTokenKey).set(permissionList.toString(),CommonConstants.REDIS_KEY_DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
			for (JsonElement jsonElement : permissionList) {
				if (matchUrl(requestUrl,requestMethod,jsonElement.getAsJsonObject().get("url").getAsString())) {
					return true;
				}
			}
		}
		return false;
	}

}
