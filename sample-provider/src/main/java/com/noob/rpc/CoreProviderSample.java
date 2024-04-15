package com.noob.rpc;

import com.noob.rpc.common.service.UserService;
import com.noob.rpc.config.RegistryConfig;
import com.noob.rpc.config.RpcConfig;
import com.noob.rpc.model.ServiceMetaInfo;
import com.noob.rpc.registry.LocalRegistry;
import com.noob.rpc.registry.Registry;
import com.noob.rpc.registry.RegistryFactory;
import com.noob.rpc.server.HttpServer;
import com.noob.rpc.server.VertxHttpServer;
import com.noob.rpc.server.tcp.VertxTcpServer;
import com.noob.rpc.service.UserServiceImpl;

/**
 * 服务提供者启动类，通过main方法编写提供服务的代码
 */
public class CoreProviderSample {
    public static void main(String[] args) {
        // 框架初始化
        RpcApplication.init();

        // 服务注册
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动web服务（从RPC框架中的全局配置中获取端口）
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
