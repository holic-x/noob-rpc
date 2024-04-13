package com.noob.rpc;

import com.noob.rpc.serializer.Serializer;
import com.noob.rpc.serializer.SerializerFactory;

import java.util.ServiceLoader;

/**
 * SPI机制测试
 */
public class SPITest {
    public static void main(String[] args) {
        // 方式1：系统实现
        Serializer serializer = null;
        ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
        for (Serializer service : serviceLoader) {
            serializer = service;
        }
        System.out.println(serializer);

        // 方式2：动态获取序列化器
//        final Serializer s = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
    }
}
