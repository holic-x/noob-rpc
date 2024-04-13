package com.noob.rpc.proxy;

import com.noob.rpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 *  服务代理工厂（用于创建代理对象）
 */
public class ServiceProxyFactory {
    /**
     * 根据服务类获取代理对象
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {

        // 校验配置参数，如果mock为true则走mock代理
        if(RpcApplication.getRpcConfig().isMock()){
            return MockServiceProxyFactory.getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
