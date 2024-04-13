package com.noob.rpc.service;

import com.noob.rpc.common.model.User;
import com.noob.rpc.common.service.UserService;

/**
 * 用户接口实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名："+user.getName());
        return user;
    }
}
