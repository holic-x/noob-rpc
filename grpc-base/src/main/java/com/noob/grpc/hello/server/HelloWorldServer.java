package com.noob.grpc.hello.server;


import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class HelloWorldServer {

  private Server server;

  /**
  * 对外暴露服务
  **/
  private void start() throws IOException {
    int port = 50051;
    server = ServerBuilder.forPort(port)
        .addService(new HelloServiceImpl())
        .build()
        .start();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        HelloWorldServer.this.stop();
      }
    });
  }

  /**
  * 关闭端口
  **/
  private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }

  /**
  * 优雅关闭
  **/
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }


  public static void main(String[] args) throws IOException, InterruptedException {
    final HelloWorldServer server = new HelloWorldServer();
    server.start();
    server.blockUntilShutdown();
  }
  
}