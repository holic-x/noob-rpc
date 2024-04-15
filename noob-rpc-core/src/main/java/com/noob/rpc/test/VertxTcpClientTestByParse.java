package com.noob.rpc.test;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;

/**
 * Vertx TCP 请求客户端
 */
public class VertxTcpClientTestByParse {

    public static void main(String[] args) {
        new VertxTcpClientTestByParse().start();
    }

    /**
     * 发送请求
     */
    public void start() {
        // 创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8888, "localhost", result -> {
            if (result.succeeded()) {
                System.err.println("connect to TCP server");
                io.vertx.core.net.NetSocket socket = result.result();

                // 发送数据(模拟发送1000次请求)
                for (int i = 0; i < 1000; i++) {
                    Buffer buffer = Buffer.buffer();
                    String str = "hello noob!hello noob!hello noob!hello noob!";
                    buffer.appendInt(0);
                    buffer.appendInt(str.getBytes().length);
                    buffer.appendBytes(str.getBytes());
                    socket.write(buffer);
                }

                // 接收响应
                socket.handler(buffer -> {
                    System.out.println("received response from server: " + buffer.toString());
                });
            } else {
                System.err.println("fail to connect to TCP server");
            }
        });

    }
}

