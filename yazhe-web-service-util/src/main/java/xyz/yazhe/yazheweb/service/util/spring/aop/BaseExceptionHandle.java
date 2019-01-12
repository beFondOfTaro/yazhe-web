package xyz.yazhe.yazheweb.service.util.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
            log.error(e.getMessage(),e);
            return ResultVOUtil.error(commonException.getCode(),commonException.getMessage());
        }
        //资源异常
        else if (e instanceof ResourceException){
			log.error(e.getMessage(),e);
            ResourceException resourceException = (ResourceException) e;
            ResourceExceptionEnum resourceExceptionEnum = resourceException.getResourceExceptionEnum();
            return ResultVOUtil.error(resourceExceptionEnum.getCode(),
                    "资源：" + resourceException.getResource() + ";" + resourceExceptionEnum.getException());
        }else if (e instanceof MethodArgumentNotValidException){
        	MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
        	BindingResult bindingResult = exception.getBindingResult();
        	StringBuilder stringBuilder = new StringBuilder();
        	stringBuilder.append("参数校验失败！");
        	bindingResult.getFieldErrors().forEach(fieldError -> {
				stringBuilder.append("[字段名：");
				stringBuilder.append(fieldError.getField());
				stringBuilder.append(",错误信息:");
				stringBuilder.append(fieldError.getDefaultMessage());
				stringBuilder.append("]");
			});
        	log.error(exception.getMessage(), e);
        	return ResultVOUtil.error(ResultEnum.PARAM_ERROR.getCode(), stringBuilder.toString());
		}
        //如果不是我们自定义的异常
        else {
            log.error("【系统异常】{}",e);
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return ResultVOUtil.error(
                    ResultEnum.UNKNOWN_ERROR.getCode(),
                    e.getMessage());
        }
    }
}
