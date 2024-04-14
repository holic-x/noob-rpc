package com.noob.rpc.registry;

import com.noob.rpc.serializer.Serializer;
import com.noob.rpc.spi.SpiLoader;

/**
 * 注册中心工厂（工厂模式,用于获取注册中心对象）
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
