package com.noob.rpc.test;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;

/**
 * Vertx TCP 服务器
 */
@Slf4j
public class VertxTcpServerTestByParse {

    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(socket -> {
            // 处理连接
            socket.handler(buffer -> {
                // 构造parse
                RecordParser parser = RecordParser.newFixed(8);
                parser.setOutput(new Handler<Buffer>() {
                    // 初始化
                    int size = -1;
                    // 一次完整的读取（头+体）
                    Buffer resultBuffer = Buffer.buffer();

                    @Override
                    public void handle(Buffer buffer) {
                        if (-1 == size) {
                            // 读取消息体长度
                            size = buffer.getInt(4);
                            parser.fixedSizeMode(size);
                            // 写入头信息到结果
                            resultBuffer.appendBuffer(buffer);
                        } else {
                            // 写入体信息到结果
                            resultBuffer.appendBuffer(buffer);
                            System.out.println(resultBuffer.toString());
                            // 重置一轮
                            parser.fixedSizeMode(8);
                            size = -1;
                            resultBuffer = Buffer.buffer();
                        }
//                        System.out.println(resultBuffer);
                    }
                });
                // 使用parse
                socket.handler(parser);
            });
        });

        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("TCP server started on port " + port);
            } else {
                log.info("Failed to start TCP server: " + result.cause());
            }
        });
    }

    /**
     * 编写处理请求逻辑（结合实际业务场景编写）
     * @param requestData
     * @return
     */
    private byte[] handleRequest(byte[] requestData) {
        return "hello Vertx Server".getBytes();
    }

    public static void main(String[] args) {
        new VertxTcpServerTestByParse().doStart(8888);
    }
}
