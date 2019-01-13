package xyz.yazhe.yazheweb.service.domain.exception;

import lombok.Getter;

/**
 * 通用异常
 * @author BeFondOfTaro
 * Created at 13:56 2018/3/14
 */
@Getter
public class BusinessException extends RuntimeException{

	public BusinessException(String message) {
		super(message);
	}
}
