package xyz.yazhe.yazheweb.config.exception;

import lombok.Getter;
import xyz.yazhe.yazheweb.constants.ResultEnum;

/**
 * 通用异常
 * @author BeFondOfTaro
 * Created at 13:56 2018/3/14
 */
@Getter
public class CommonException extends RuntimeException{

    private Integer code;

    public CommonException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public CommonException(Integer code, String message){
        super(message);
        this.code = code;
    }
}
