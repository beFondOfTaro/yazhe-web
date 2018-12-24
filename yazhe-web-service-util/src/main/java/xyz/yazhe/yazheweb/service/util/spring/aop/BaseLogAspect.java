package xyz.yazhe.yazheweb.service.util.spring.aop;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.yazhe.yazheweb.service.domain.common.constants.HttpParamKey;
import xyz.yazhe.yazheweb.service.util.GsonUtil;
import xyz.yazhe.yazheweb.service.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yazhe
 * Created at 17:08 2018/8/14
 */
@Aspect
public class BaseLogAspect {

	private Gson gson = GsonUtil.getGson();
	private static final Logger logger = LoggerFactory.getLogger(BaseLogAspect.class);

	//定义切点
	@Pointcut("execution(public * xyz.yazhe.yazheweb.service..*.controller.*.*(..))")
	public void pointCut(){

	}

	@Around("pointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest req = ra.getRequest();
		HttpServletResponse res = ra.getResponse();
		String token = req.getHeader(HttpParamKey.TOKEN);
		Map<String,Object> reqMap = new HashMap<>();
		if (!StringUtils.isEmpty(token)){
			reqMap.put("userId", JWTUtil.getUserId(token));
			reqMap.put("token",token);
		}
		reqMap.put("url",req.getRequestURI());
		reqMap.put("method",req.getMethod());
		reqMap.put("requestParams", Arrays.toString(pjp.getArgs()));
		logger.info("controller请求：" + gson.toJson(reqMap));
		//result的值就是被拦截方法的返回值
		Object result = pjp.proceed();
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("status",res.getStatus());
		resMap.put("returnValue",result);
		long endTime = System.currentTimeMillis();
		resMap.put("totalTime",endTime - startTime);
		logger.info("controller响应：" + gson.toJson(resMap));
		return result;
	}
}
