package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.user.auth.service.WebStatisticService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;


/**
 * 网站统计
 * @author Yazhe
 * Created at 17:05 2018/8/31
 */
@RestController
public class WebStatisticController {

	@Autowired
	private WebStatisticService webStatisticService;

	/**
	 * 网站点击数加一
	 * @return 当前点击数
	 */
	@PostMapping("clicks-inc")
	public ResultVO clicksInc(){
		return ResultVOUtil.success(webStatisticService.clicksInc());
	}
}
