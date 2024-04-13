package com.noob.rpc;

import com.noob.rpc.common.model.User;
import com.noob.rpc.common.service.UserService;

public class EasyConsumerSample {
    public static void main(String[] args) {
        // todo 获取UserService的实现对象
        UserService userService = null;
        User user = new User("noob");

        // 调用
        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user == null");
        }
    }
}
