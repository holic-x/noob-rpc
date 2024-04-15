package com.noob.rpc.config;

import com.noob.rpc.fault.retry.RetryStrategyKeys;
import com.noob.rpc.fault.tolerant.TolerantStrategyKeys;
import com.noob.rpc.loadbalancer.LoadBalancerKeys;
import com.noob.rpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架全局配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "noob-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡配置
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略配置
     */
    private String retryStrategy = RetryStrategyKeys.NO;

    /**
     * 容错策略配置
     */
    private String tolerantStrategy = TolerantStrategyKeys.FAIL_FAST;

}
