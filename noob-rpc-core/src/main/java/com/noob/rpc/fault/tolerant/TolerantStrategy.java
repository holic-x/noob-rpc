package com.noob.rpc.fault.tolerant;

import com.noob.rpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 */
public interface TolerantStrategy {

    /**
     * 容错
     *
     * @param context 上下文，用于传递数据
     * @param e       异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
