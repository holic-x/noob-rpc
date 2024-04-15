package com.noob.rpc.service;

import com.noob.rpc.common.model.User;
import com.noob.rpc.common.service.UserService;
import com.noob.rpc.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * 用户接口实现类
 */
@RpcService
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名："+user.getName());
        return user;
    }
}
