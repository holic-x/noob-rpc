package com.noob.rpc.base.proxy;


import java.lang.reflect.Proxy;

/**
 * 代理测试
 * todo https://www.jb51.net/article/252277.htm
 */
public class TestProxy {

    public static void main(String[] args) {
        // 构建代理器
        JDKProxy jdkProxy = new JDKProxy(new RealHello());
//        ClassLoader classLoader = ClassLoaderUtils.getCurrentClassLoader();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 把生成的代理类保存到文件
        System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 生成代理类
        Hello test = (Hello) Proxy.newProxyInstance(classLoader, new Class[]{Hello.class}, jdkProxy);
        // 方法调用
        System.out.println(test.say());
    }

}
