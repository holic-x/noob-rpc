package com.noob.rpc.base.proxy;


import java.lang.reflect.Proxy;

/**
 * 代理测试
 */
public class TestProxy {

    public static void main(String[] args) {
        // 构建代理器
        JDKProxy jdkProxy = new JDKProxy(new RealTarget());
        // ClassLoader classLoader = ClassLoaderUtils.getCurrentClassLoader();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 把生成的代理类保存到文件
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 生成代理类
        Target target = (Target) Proxy.newProxyInstance(classLoader, new Class[]{Target.class}, jdkProxy);
        // 方法调用
        System.out.println(target.say());
    }

}
