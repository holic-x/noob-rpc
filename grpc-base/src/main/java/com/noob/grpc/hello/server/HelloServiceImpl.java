package com.noob.grpc.hello.server;

import com.noob.grpc.hello.HelloReply;
import com.noob.grpc.hello.HelloRequest;
import com.noob.grpc.hello.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

// 服务提供方代码
class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void say(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}