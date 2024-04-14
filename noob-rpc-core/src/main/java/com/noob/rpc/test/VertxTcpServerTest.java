package com.noob.rpc.test;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Vertx TCP 服务器
 */
@Slf4j
public class VertxTcpServerTest {

    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(socket->{
            // 处理连接
            socket.handler(buffer -> {
                /*
               // 处理接收到的字节数组
                byte[] requestData = buffer.getBytes();
                // 自定义字节数组处理逻辑（例如解析请求、调用服务、构造响应等）
                byte[] responseData = handleRequeset(requestData);
                // 发送响应(向连接到服务器的客户端发送数据,数据格式为Buffer（Vertx提供的字节数组缓冲区实现）)
                socket.write(Buffer.buffer(responseData));
                 */
                String testMessage = "hello noob!hello noob!hello noob!hello noob!";
                int messageLength = testMessage.getBytes().length;
                int bufferLength = buffer.getBytes().length;
                if(bufferLength<messageLength){
                    System.out.println("半包,length="+bufferLength);
                    return;
                }
                if(bufferLength>messageLength){
                    System.out.println("粘包,length="+bufferLength);
                    return;
                }
                String str = new String(buffer.getBytes(0, messageLength));
                System.out.println(str);
                if(testMessage.equals(str)){
                    System.out.println("数据接收正常");
                }
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
        new VertxTcpServerTest().doStart(8888);
    }
}
