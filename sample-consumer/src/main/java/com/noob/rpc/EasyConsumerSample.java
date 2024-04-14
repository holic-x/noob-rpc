package com.noob.rpc;

import com.noob.rpc.common.model.User;
import com.noob.rpc.common.service.UserService;
import com.noob.rpc.proxy.ServiceProxy;
import com.noob.rpc.proxy.ServiceProxyFactory;
import com.noob.rpc.proxy.UserServiceProxy;

/**
 * 消费者调用请求
 */
public class EasyConsumerSample {
    public static void main(String[] args) {
        // todo 获取UserService的实现对象
        // 动态代理模式
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        // 静态代理模式
//        UserService userService = new UserServiceProxy();


        User user = new User("noob");

        // 单次调用
//        User newUser = userService.getUser(user);
//        if(newUser != null) {
//            System.out.println(newUser.getName());
//        }else {
//            System.out.println("user == null");
//        }

        // 单次调用
        for(int i=0;i<5;i++){
            User newUser = userService.getUser(user);
            if(newUser != null) {
                System.out.println(newUser.getName());
            }else {
                System.out.println("user == null");
            }
        }
    }
}
