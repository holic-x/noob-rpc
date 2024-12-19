package com.noob.grpc.hello.client;

import com.noob.grpc.hello.HelloReply;
import com.noob.grpc.hello.HelloRequest;
import com.noob.grpc.hello.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

public class HelloWorldClient {

    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;
    /**
    * 构建Channel连接
    **/
    public HelloWorldClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    /**
    * 构建Stub用于发请求
    **/
    HelloWorldClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }
    
    /**
    * 调用完手动关闭
    **/
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

 
    /**
    * 发送rpc请求
    **/
    public void say(String name) {
        // 构建入参对象
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            // 发送请求
            response = blockingStub.say(request);
        } catch (StatusRuntimeException e) {
            return;
        }
        System.out.println(response);
    }

    public static void main(String[] args) throws Exception {
            HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
            try {
                client.say("world");
            } finally {
                client.shutdown();
            }
    }
}