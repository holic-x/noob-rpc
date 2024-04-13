package com.noob.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 *  服务代理工厂（用于创建代理对象）
 */
public class MockServiceProxyFactory {
    /**
     * 根据服务类获取 Mock 代理对象
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy());
    }
}
