package com.noob.rpc;

import com.noob.rpc.common.service.UserService;
import com.noob.rpc.registry.LocalRegistry;
import com.noob.rpc.server.HttpServer;
import com.noob.rpc.server.VertxHttpServer;
import com.noob.rpc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        // 框架初始化
        RpcApplication.init();

        System.out.println("Hello world!");// 提供服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动web服务
        HttpServer httpServer = new VertxHttpServer();
        // 启动web服务（从RPC框架中的全局配置中获取端口）
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

    }
}