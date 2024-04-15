package com.noob.rpc.starter.annotation;

import com.noob.rpc.starter.bootstrap.RpcConsumerBootstrap;
import com.noob.rpc.starter.bootstrap.RpcInitBootstrap;
import com.noob.rpc.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 Rpc 注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 是否需要启动 server
     *
     * @return
     */
    boolean needServer() default true;
}
