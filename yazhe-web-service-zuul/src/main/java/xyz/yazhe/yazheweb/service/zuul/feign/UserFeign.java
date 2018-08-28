package xyz.yazhe.yazheweb.service.zuul.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.zuul.feign.fallback.UserFeignFallBack;

/**
 * @author Yazhe
 * Created at 16:14 2018/8/28
 */
@FeignClient(value = "service-user-auth",fallback = UserFeignFallBack.class)
public interface UserFeign {

	@RequestMapping(value = "permission",method = RequestMethod.GET)
	ResultVO listPermission();
}
