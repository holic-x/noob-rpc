package com.noob.rpc.server.tcp;

import com.noob.rpc.model.RpcRequest;
import com.noob.rpc.model.RpcResponse;
import com.noob.rpc.protocol.ProtocolMessage;
import com.noob.rpc.protocol.ProtocolMessageDecoder;
import com.noob.rpc.protocol.ProtocolMessageEncoder;
import com.noob.rpc.protocol.ProtocolMessageTypeEnum;
import com.noob.rpc.registry.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

public class TcpServerHandler implements Handler<NetSocket> {
    /**
     * 处理请求
     *
     * @param netSocket the event to handle
     */
    @Override
    public void handle(NetSocket netSocket) {
        //处理连接
        netSocket.handler(buffer -> {
            //接受请求，解码
            ProtocolMessage<RpcRequest> protocolMessage;
            try {
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (IOException e) {
                throw new RuntimeException(" 协议消息解码错误");
            }
            RpcRequest rpcRequest = protocolMessage.getBody();

            //处理请求
            //构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            try {
                //获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                //封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage(" ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            // 发送响应，编码
            ProtocolMessage.Header header = protocolMessage.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
            try {
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                netSocket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException(" 协议消息编码错误");
            }
        });
    }
}


