package com.noob.rpc.base.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 代理类
 */
public class JDKProxy implements InvocationHandler {

    private Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before method invoke");
        Object res = ((RealTarget) target).invoke();
        System.out.println("after method invoke");
        return res;
    }
}
