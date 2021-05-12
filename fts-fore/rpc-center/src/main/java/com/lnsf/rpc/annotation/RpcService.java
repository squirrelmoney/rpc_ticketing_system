package com.lnsf.rpc.annotation;


import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @program: rpc-framwork
 * @description: RPC服务注释，标记在服务实现类上
 * @author: money
 * @create: 2021-03-25 11:16
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 服务版本，默认值为空字符串
     */
    String version() default "";

    /**
     * 服务组，默认值为空字符串
     */
    String group() default "";

}
