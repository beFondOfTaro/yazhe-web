package xyz.yazhe.yazheweb.service.util.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.yazhe.yazheweb.service.util.JWTUtil;

/**
 * @author Yazhe
 * Created at 15:51 2018/12/14
 */
public class RequestUtil {

	/**
	 * 获取当前用户d
	 * @return
	 */
	public static String getCurrentUserId(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return JWTUtil.getUserId(requestAttributes.getRequest().getHeader("token"));
	}
}
