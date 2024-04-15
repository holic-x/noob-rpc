package com.noob.rpc;

import com.noob.rpc.bootstrap.ProviderBootstrap;
import com.noob.rpc.common.service.UserService;
import com.noob.rpc.config.RegistryConfig;
import com.noob.rpc.config.RpcConfig;
import com.noob.rpc.model.ServiceMetaInfo;
import com.noob.rpc.model.ServiceRegisterInfo;
import com.noob.rpc.registry.LocalRegistry;
import com.noob.rpc.registry.Registry;
import com.noob.rpc.registry.RegistryFactory;
import com.noob.rpc.server.tcp.VertxTcpServer;
import com.noob.rpc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供者启动类，通过main方法编写提供服务的代码
 */
public class CoreProviderSampleByBootstrap {
    public static void main(String[] args) {
        // 定义要初始化的服务列表
        List<ServiceRegisterInfo> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(),UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 框架初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
