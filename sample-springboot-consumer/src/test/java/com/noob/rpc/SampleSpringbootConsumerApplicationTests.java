package com.noob.rpc;

import com.noob.rpc.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

//@SpringBootTest(classes = SampleSpringbootConsumerApplication.class)
@SpringBootTest
class SampleSpringbootConsumerApplicationTests {

    @Resource
    private UserServiceImpl userService;

    @Test
    void contextLoads() {
        userService.getName();
    }

}
