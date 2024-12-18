package com.noob.grpc.demo;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Descriptors;
import io.grpc.BindableService;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import io.grpc.MethodDescriptor.MethodType;
import io.grpc.protobuf.ProtoFileDescriptorSupplier;
import io.grpc.protobuf.ProtoMethodDescriptorSupplier;
import io.grpc.protobuf.ProtoServiceDescriptorSupplier;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.annotations.RpcMethod;

public final class GreeterGrpc {
    public static final String SERVICE_NAME = "helloworld.Greeter";
    private static volatile MethodDescriptor<HelloRequest, HelloReply> getSayHelloMethod;
    private static final int METHODID_SAY_HELLO = 0;
    private static volatile ServiceDescriptor serviceDescriptor;

    private GreeterGrpc() {
    }

    @RpcMethod(
        fullMethodName = "helloworld.Greeter/SayHello",
        requestType = HelloRequest.class,
        responseType = HelloReply.class,
        methodType = MethodType.UNARY
    )
    public static MethodDescriptor<HelloRequest, HelloReply> getSayHelloMethod() {
        MethodDescriptor getSayHelloMethod;
        if ((getSayHelloMethod = GreeterGrpc.getSayHelloMethod) == null) {
            Class var1 = GreeterGrpc.class;
            synchronized(GreeterGrpc.class) {
                if ((getSayHelloMethod = GreeterGrpc.getSayHelloMethod) == null) {
                    GreeterGrpc.getSayHelloMethod = getSayHelloMethod = MethodDescriptor.newBuilder().setType(MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName("helloworld.Greeter", "SayHello")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoUtils.marshaller(HelloRequest.getDefaultInstance())).setResponseMarshaller(ProtoUtils.marshaller(HelloReply.getDefaultInstance())).setSchemaDescriptor(new GreeterMethodDescriptorSupplier("SayHello")).build();
                }
            }
        }

        return getSayHelloMethod;
    }

    public static GreeterStub newStub(Channel channel) {
        return new GreeterStub(channel);
    }

    public static GreeterBlockingStub newBlockingStub(Channel channel) {
        return new GreeterBlockingStub(channel);
    }

    public static GreeterFutureStub newFutureStub(Channel channel) {
        return new GreeterFutureStub(channel);
    }

    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            Class var1 = GreeterGrpc.class;
            synchronized(GreeterGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = ServiceDescriptor.newBuilder("helloworld.Greeter").setSchemaDescriptor(new GreeterFileDescriptorSupplier()).addMethod(getSayHelloMethod()).build();
                }
            }
        }

        return result;
    }

    private static final class GreeterMethodDescriptorSupplier extends GreeterBaseDescriptorSupplier implements ProtoMethodDescriptorSupplier {
        private final String methodName;

        GreeterMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        public Descriptors.MethodDescriptor getMethodDescriptor() {
            return this.getServiceDescriptor().findMethodByName(this.methodName);
        }
    }

    private static final class GreeterFileDescriptorSupplier extends GreeterBaseDescriptorSupplier {
        GreeterFileDescriptorSupplier() {
        }
    }

    private abstract static class GreeterBaseDescriptorSupplier implements ProtoFileDescriptorSupplier, ProtoServiceDescriptorSupplier {
        GreeterBaseDescriptorSupplier() {
        }

        public Descriptors.FileDescriptor getFileDescriptor() {
            return HelloWorldProto.getDescriptor();
        }

        public Descriptors.ServiceDescriptor getServiceDescriptor() {
            return this.getFileDescriptor().findServiceByName("Greeter");
        }
    }

    private static final class MethodHandlers<Req, Resp> implements ServerCalls.UnaryMethod<Req, Resp>, ServerCalls.ServerStreamingMethod<Req, Resp>, ServerCalls.ClientStreamingMethod<Req, Resp>, ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final GreeterImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(GreeterImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        public void invoke(Req request, StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                case 0:
                    this.serviceImpl.sayHello((HelloRequest)request, responseObserver);
                    return;
                default:
                    throw new AssertionError();
            }
        }

        public StreamObserver<Req> invoke(StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    public static final class GreeterFutureStub extends AbstractStub<GreeterFutureStub> {
        private GreeterFutureStub(Channel channel) {
            super(channel);
        }

        private GreeterFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        protected GreeterFutureStub build(Channel channel, CallOptions callOptions) {
            return new GreeterFutureStub(channel, callOptions);
        }

        public ListenableFuture<HelloReply> sayHello(HelloRequest request) {
            return ClientCalls.futureUnaryCall(this.getChannel().newCall(GreeterGrpc.getSayHelloMethod(), this.getCallOptions()), request);
        }
    }

    public static final class GreeterBlockingStub extends AbstractStub<GreeterBlockingStub> {
        private GreeterBlockingStub(Channel channel) {
            super(channel);
        }

        private GreeterBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        protected GreeterBlockingStub build(Channel channel, CallOptions callOptions) {
            return new GreeterBlockingStub(channel, callOptions);
        }

        public HelloReply sayHello(HelloRequest request) {
            return (HelloReply)ClientCalls.blockingUnaryCall(this.getChannel(), GreeterGrpc.getSayHelloMethod(), this.getCallOptions(), request);
        }
    }

    public static final class GreeterStub extends AbstractStub<GreeterStub> {
        private GreeterStub(Channel channel) {
            super(channel);
        }

        private GreeterStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        protected GreeterStub build(Channel channel, CallOptions callOptions) {
            return new GreeterStub(channel, callOptions);
        }

        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            ClientCalls.asyncUnaryCall(this.getChannel().newCall(GreeterGrpc.getSayHelloMethod(), this.getCallOptions()), request, responseObserver);
        }
    }

    public abstract static class GreeterImplBase implements BindableService {
        public GreeterImplBase() {
        }

        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(GreeterGrpc.getSayHelloMethod(), responseObserver);
        }

        public final ServerServiceDefinition bindService() {
            return ServerServiceDefinition.builder(GreeterGrpc.getServiceDescriptor()).addMethod(GreeterGrpc.getSayHelloMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(this, 0))).build();
        }
    }
}
