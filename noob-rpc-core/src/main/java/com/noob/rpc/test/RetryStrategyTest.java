package com.noob.rpc.test;

import com.noob.rpc.fault.retry.FixedIntervalRetryStrategy;
import com.noob.rpc.model.RpcResponse;
import com.noob.rpc.fault.retry.NoRetryStrategy;
import com.noob.rpc.fault.retry.RetryStrategy;
import org.junit.Test;

/**
 * 重试策略测试
 */
public class RetryStrategyTest {

    // 指定重试策略进行测试
//    RetryStrategy retryStrategy = new NoRetryStrategy();
    RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}