package xyz.yazhe.yazheweb.service.util.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.yazhe.yazheweb.service.domain.base.ResultVO;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResourceExceptionEnum;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;
import xyz.yazhe.yazheweb.service.domain.exception.CommonException;
import xyz.yazhe.yazheweb.service.domain.exception.ResourceException;
import xyz.yazhe.yazheweb.service.util.web.result.ResultVOUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller的统一异常处理
 * @author BeFondOfTaro
 * Created at 23:00 2018/3/15
 */
@Slf4j
public class BaseExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handle(Exception e, HttpServletResponse response){
        //如果是我们自定义的异常
        if (e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            return ResultVOUtil.error(commonException.getCode(),commonException.getMessage());
        }
        //资源异常
        else if (e instanceof ResourceException){
            ResourceException resourceException = (ResourceException) e;
            ResourceExceptionEnum resourceExceptionEnum = resourceException.getResourceExceptionEnum();
            return ResultVOUtil.error(resourceExceptionEnum.getCode(),
                    "资源：" + resourceException.getResource() + ";" + resourceExceptionEnum.getException());
        }
        //如果不是我们自定义的异常
        else {
            log.error("【系统异常】{}",e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ResultVOUtil.error(
                    ResultEnum.UNKNOWN_ERROR.getCode(),
                    e.getMessage());
        }
    }
}
