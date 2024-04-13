package com.noob.rpc;

import com.noob.rpc.server.HttpServer;
import com.noob.rpc.server.VertxHttpServer;

public class Main {
    public static void main(String[] args) {
        // 启动web服务测试
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}