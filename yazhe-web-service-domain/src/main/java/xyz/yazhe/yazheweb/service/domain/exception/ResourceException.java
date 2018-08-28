package xyz.yazhe.yazheweb.service.domain.exception;

import lombok.Getter;
import xyz.yazhe.yazheweb.service.domain.common.constants.exception.ResourceExceptionEnum;

/**
 * 资源异常
 * @author BeFondOfTaro
 * Created at 14:23 2018/4/23
 */
@Getter
public class ResourceException extends RuntimeException {

    /**
     * 异常类型枚举
     */
    private ResourceExceptionEnum resourceExceptionEnum;

    /**
     * 资源
     */
    private String resource;

    public ResourceException(ResourceExceptionEnum resourceExceptionEnum, String resource) {
        this.resourceExceptionEnum = resourceExceptionEnum;
        this.resource = resource;
    }
}
