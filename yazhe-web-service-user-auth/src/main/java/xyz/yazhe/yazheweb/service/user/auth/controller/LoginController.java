package xyz.yazhe.yazheweb.service.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.user.auth.service.LoginService;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

/**
 * @author BeFondOfTaro
 * Created at 23:40 2018/5/16
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("login")
    public ResultVO login(@RequestParam String identifier,
						  @RequestParam String credential,
						  @RequestParam(defaultValue = "0") Integer identifyType){
        return ResultVOUtil.success(loginService.login(identifier, credential, identifyType));
    }
}
