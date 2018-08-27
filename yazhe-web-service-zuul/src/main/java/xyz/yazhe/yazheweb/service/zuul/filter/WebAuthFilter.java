package xyz.yazhe.yazheweb.service.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import xyz.yazhe.yazheweb.service.domain.common.constants.CommonConstants;
import xyz.yazhe.yazheweb.service.domain.common.constants.HttpParamKey;
import xyz.yazhe.yazheweb.service.domain.common.constants.ResultEnum;
import xyz.yazhe.yazheweb.service.util.GsonUtil;
import xyz.yazhe.yazheweb.service.util.JWTUtil;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	@Value("filter.web-auth.ignored-url")
	private String ignoredUrls;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Override
	public Object run() {
		logger.info("过滤");
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();
		HttpServletResponse response = requestContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		//判断不进行过滤的url
		String requestUrl = request.getRequestURI();
		for (String ignoredUrl : ignoredUrls.split(",")){
			if (requestUrl.contains(ignoredUrl)){
				return null;
			}
		}
		//获取token进行校验
		String token = request.getHeader(HttpParamKey.TOKEN);

		String userId = JWTUtil.getUserId(token);
		if (userId == null || !token.equals(redisTemplate.boundValueOps(CommonConstants.RedisKey.AUTH_TOKEN_PRIFIX + userId).get())){
			//返回错误
			requestContext.setResponseBody(GsonUtil.getGson().toJson(ResultVOUtil.error(ResultEnum.INVALID_TOKEN)));
			requestContext.setResponseStatusCode(401);
			requestContext.sendZuulResponse();
			return null;
		}
		//获取用户权限
		redisTemplate.getClientList().forEach((redisClientInfo -> logger.info(redisClientInfo.getAddressPort())));
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

}
