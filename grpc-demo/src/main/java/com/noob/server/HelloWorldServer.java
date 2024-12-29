package com.noob.server;

import com.noob.grpc.demo.GreeterGrpc;
import com.noob.grpc.demo.HelloReply;
import com.noob.grpc.demo.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * HelloWorldServer
 **/
public class HelloWorldServer {

    private static final Logger log = Logger.getLogger(HelloWorldServer.class.getName());


    //扩展gRPC自动生成的服务接口,实现业务功能
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

            //构建响应消息,从请求消息中获取姓名,在前面拼接上"Hello "
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();

            //在流关闭或抛出异常前可以调用多次
            responseObserver.onNext(reply);

            //关闭流
            responseObserver.onCompleted();

        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        //服务要监听的端口
        int port = 50051;

        //创建服务对象,监听端口,注册服务并启动
        Server server = ServerBuilder.
                forPort(port)  //监听50051端口
                .addService(new GreeterImpl()) //注册服务
                .build()  //创建Server对象
                .start(); //启动

        log.info("Server started,listening on " + port);

        server.awaitTermination();

    }

}