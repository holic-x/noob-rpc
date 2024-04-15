package com.noob.rpc;

import com.noob.rpc.config.RpcConfig;

public class Main {
    public static void main(String[] args) {
        // 测试配置文件读取
        RpcConfig rpc = RpcApplication.getRpcConfig();
        System.out.println(rpc);
    }
}