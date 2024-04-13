package com.noob.rpc.common.service;

import com.noob.rpc.common.model.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 获取用户信息
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * mock测试
     */
    default short getNumber(){
        return -1;
    }
}
