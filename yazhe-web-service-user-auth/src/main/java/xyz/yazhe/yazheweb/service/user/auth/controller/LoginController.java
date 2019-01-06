package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.base.validation.group.UserValidatedGroup.RegisterAccount;
import xyz.yazhe.yazheweb.service.domain.user.auth.RO.UserRO;
import xyz.yazhe.yazheweb.service.user.auth.service.LoginService;
import xyz.yazhe.yazheweb.service.user.auth.service.UserService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

/**
 * @author BeFondOfTaro
 * Created at 23:40 2018/5/16
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
	private UserService userService;

	/**
	 * TODO 未进行参数校验
	 * @param identifier
	 * @param credential
	 * @param identifyType
	 * @return
	 */
    @PostMapping("login")
    public ResultVO login(@RequestParam String identifier,
						  @RequestParam String credential,
						  @RequestParam(defaultValue = "0") Integer identifyType){
        return ResultVOUtil.success(loginService.login(identifier, credential, identifyType));
    }

    @PostMapping("register-account")
    public ResultVO registerAccount(@Validated(RegisterAccount.class) @RequestBody UserRO userRO){
    	//设置为普通用户
    	userRO.setRoleId("2");
    	userService.addUser(userRO);
    	return ResultVOUtil.success();
	}
}
