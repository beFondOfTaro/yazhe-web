package xyz.yazhe.yazheweb.service.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundListOperations;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 身份认证过滤器
 * @author Yazhe
 * Created at 16:36 2018/8/27
 */
@Component
public class WebAuthFilter extends ZuulFilter{

	private static final Logger logger = LoggerFactory.getLogger(WebAuthFilter.class);

	/**
	 * 不进行过滤的url
	 */
	@Value("${filter.web-auth.ignored-mappings}")
	private String ignoredMappings;
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
	private RedisTemplate<String,String> stringRedisTemplate;
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
		for (String ignoredMapping : ignoredMappings.split(",")){
			String ignoredUrl;
			String mappedMethods;
			int leftIdx = ignoredMapping.indexOf("[");
			int rightIdx = ignoredMapping.indexOf("]");
			//如果存在[]，则需要匹配method
			if (leftIdx > -1 && rightIdx > -1){
				ignoredUrl = ignoredMapping.substring(0,leftIdx);
				mappedMethods = ignoredMapping.substring(leftIdx+1,rightIdx).toLowerCase();
				if (ignoredUrl.contains(requestUrl) && mappedMethods.contains(requestMethod)){
					return null;
				}
			}else {
				//不存在[]，直接匹配url
				ignoredUrl = ignoredMapping;
				if (ignoredUrl.contains(requestUrl)){
					return null;
				}
			}
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
			if (userId == null || !token.equals(stringRedisTemplate.boundValueOps(CommonConstants.RedisKey.AUTH_TOKEN_PREFIX + userId).get())){
				//返回错误
				throw new CommonException(ResultEnum.TOKEN_EXPIRED);
			}
		}
		catch (CommonException e){
			requestContext.setResponseBody(gson.toJson(ResultVOUtil.error(e.getCode(),e.getMessage())));
			requestContext.setResponseStatusCode(401);
			requestContext.setSendZuulResponse(false);
			return null;
		}
		//校验用户权限
		if (!checkPermission(userId,requestUrl)){
			requestContext.setResponseBody(gson.toJson(ResultVOUtil.error(ResultEnum.UNAUTHORIZED)));
			requestContext.setResponseStatusCode(HttpServletResponse.SC_UNAUTHORIZED);
			requestContext.setSendZuulResponse(false);
		}
		return null;
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
	 * @return
	 */
	private boolean checkPermission(String userId,String requestUrl){
		String userTokenKey = CommonConstants.RedisKey.USER_PERMISSION_PREFIX + userId;
		//先查询缓存
		if (redisTemplate.hasKey(userTokenKey)){
			BoundListOperations<String,Object> boundListOperations = redisTemplate.boundListOps(userTokenKey);
			List<Object> permissionList = boundListOperations.range(0,boundListOperations.size());
			for (Object permission : permissionList) {
				if (((Permission)permission).getUrl().contains(requestUrl)){
					return true;
				}
			}
		}else{
			//缓存没有那么查询数据库
			JsonArray permissionList = gson.toJsonTree(userFeign.listPermission().getData()).getAsJsonArray();
			//缓存到redis
			redisTemplate.boundListOps(userTokenKey).rightPushAll(permissionList);
			for (JsonElement jsonElement : permissionList) {
				if (jsonElement.getAsJsonObject().get("url").getAsString().contains(requestUrl)) {
					return true;
				}
			}
		}
		return false;
	}
}
