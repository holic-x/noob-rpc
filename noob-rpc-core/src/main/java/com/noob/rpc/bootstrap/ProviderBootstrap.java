package com.noob.rpc.bootstrap;
import com.noob.rpc.RpcApplication;
import com.noob.rpc.model.ServiceRegisterInfo;
import com.noob.rpc.config.RegistryConfig;
import com.noob.rpc.config.RpcConfig;
import com.noob.rpc.model.ServiceMetaInfo;
import com.noob.rpc.registry.LocalRegistry;
import com.noob.rpc.registry.Registry;
import com.noob.rpc.registry.RegistryFactory;
import com.noob.rpc.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * 服务提供者启动类
 */
public class ProviderBootstrap {

    // 框架初始化方法
//    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
    public static void init(List<ServiceRegisterInfo> serviceRegisterInfoList) {
        // 框架初始化
        RpcApplication.init();

        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动web服务（从RPC框架中的全局配置中获取端口）
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}