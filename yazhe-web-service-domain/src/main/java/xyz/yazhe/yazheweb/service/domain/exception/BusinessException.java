package xyz.yazhe.yazheweb.service.domain.exception;

import lombok.Getter;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResultEnum;

/**
 * 通用异常
 * @author BeFondOfTaro
 * Created at 13:56 2018/3/14
 */
@Getter
public class BusinessException extends RuntimeException{

	private Integer code;

	public BusinessException(String message) {
		super(message);
		code = ResultEnum.BUSINESS_EXCEPTION.getCode();
	}

	public BusinessException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		code = resultEnum.getCode();
	}
}
