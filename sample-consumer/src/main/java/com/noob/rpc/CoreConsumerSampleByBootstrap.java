package com.noob.rpc;

import com.noob.rpc.bootstrap.ConsumerBootstrap;
import com.noob.rpc.common.model.User;
import com.noob.rpc.common.service.UserService;
import com.noob.rpc.proxy.ServiceProxyFactory;

/**
 * 消费者调用请求
 */
public class CoreConsumerSampleByBootstrap {
    public static void main(String[] args) {
        // 服务消费者初始化
        ConsumerBootstrap.init();

        // 动态代理模式
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User("noob");

        // 单次调用
        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
