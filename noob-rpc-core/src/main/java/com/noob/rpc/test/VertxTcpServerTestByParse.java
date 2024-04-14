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
        server.connectHandler(socket->{
            // 处理连接
            socket.handler(buffer -> {
                String testMessage = "hello noob!hello noob!hello noob!hello noob!";
                int messageLength = testMessage.getBytes().length;

                // 构造parse
                RecordParser parser = RecordParser.newFixed(messageLength);
                parser.setOutput(new Handler<Buffer>() {
                    @Override
                    public void handle(Buffer buffer) {
                        String str = new String(buffer.getBytes());
                        System.out.println(str);
                        if(testMessage.equals(str)){
                            System.out.println("数据接收正常");
                        }
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
    private byte[] handleRequeset(byte[] requestData) {
        return "hello Vertx Server".getBytes();
    }

    public static void main(String[] args) {
        new VertxTcpServerTestByParse().doStart(8888);
    }
}
