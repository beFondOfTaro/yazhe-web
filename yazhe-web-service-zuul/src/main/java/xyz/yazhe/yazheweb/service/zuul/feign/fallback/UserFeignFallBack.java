package xyz.yazhe.yazheweb.service.zuul.feign.fallback;

import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;
import xyz.yazhe.yazheweb.service.zuul.feign.UserFeign;

/**
 * @author Yazhe
 * Created at 16:16 2018/8/28
 */
public class UserFeignFallBack implements UserFeign {
	@Override
	public ResultVO listPermission() {
		return ResultVOUtil.error(ResultEnum.UNKNOWN_ERROR);
	}
}
