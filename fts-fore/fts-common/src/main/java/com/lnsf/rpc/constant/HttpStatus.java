package com.lnsf.rpc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定义常量记录Http状态码
 */
@AllArgsConstructor
@Getter
public enum HttpStatus {
    /**
     * 成功
     */
    SUCCESS(200),

    BAD_REQUEST(400),
    /**
     * 访问受限，授权过期
     */
    FORBIDDEN(403),
    /**
     * 资源，服务未找到
     */
    NOT_FOUND(404),
    /**
     * 系统内部错误
     */
    ERROR(500);

    private final int status;

}
