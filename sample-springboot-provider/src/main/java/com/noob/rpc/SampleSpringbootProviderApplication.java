package com.noob.rpc;

import com.noob.rpc.starter.annotation.EnableRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRpc
@SpringBootApplication
public class SampleSpringbootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleSpringbootProviderApplication.class, args);
    }

}
