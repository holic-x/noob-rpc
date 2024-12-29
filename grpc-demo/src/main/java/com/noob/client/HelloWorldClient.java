package com.noob.client;

import com.noob.grpc.demo.GreeterGrpc;
import com.noob.grpc.demo.HelloReply;
import com.noob.grpc.demo.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * HelloWorldClient
 **/
public class HelloWorldClient {

    private static final Logger log = Logger.getLogger(HelloWorldClient.class.getName());


    public static void main(String[] args) {


        String host = "localhost";

        int port = 50051;

        //1.创建ManagedChannel,绑定服务端ip地址和端口
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        //2.获得同步调用的stub对象
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

//        //获得异步调用的stub对象
//        GreeterGrpc.GreeterFutureStub futureStub = GreeterGrpc.newFutureStub(channel);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            //从控制台读取用户输入
            String name = scanner.nextLine().trim();
            //构建请求消息
            HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
            //通过stub代理对象进行服务调用,获取服务端响应
            HelloReply helloReply = stub.sayHello(helloRequest);
            final String message = helloReply.getMessage();
            log.warning("Greeting: " + message);
        }
    }
}

