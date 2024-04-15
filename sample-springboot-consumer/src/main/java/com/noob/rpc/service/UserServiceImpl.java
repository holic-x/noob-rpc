package com.noob.rpc.service;

import com.noob.rpc.common.model.User;
import com.noob.rpc.common.service.UserService;
import com.noob.rpc.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

/**
 * 用户操作
 */
@Service
public class UserServiceImpl {

    @RpcReference
    private UserService userService;

    public void getName(){
        User user = new User("我的名字叫做noob");
        User resultUser = userService.getUser(user);
        System.out.printf(resultUser.getName());
    }
}
