package com.noob.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.noob.rpc.RpcApplication;
import com.noob.rpc.config.RpcConfig;
import com.noob.rpc.constant.RpcConstant;
import com.noob.rpc.fault.retry.RetryStrategy;
import com.noob.rpc.fault.retry.RetryStrategyFactory;
import com.noob.rpc.fault.tolerant.TolerantStrategy;
import com.noob.rpc.fault.tolerant.TolerantStrategyFactory;
import com.noob.rpc.loadbalancer.LoadBalancer;
import com.noob.rpc.loadbalancer.LoadBalancerFactory;
import com.noob.rpc.model.RpcRequest;
import com.noob.rpc.model.RpcResponse;
import com.noob.rpc.model.ServiceMetaInfo;
import com.noob.rpc.protocol.*;
import com.noob.rpc.registry.Registry;
import com.noob.rpc.registry.RegistryFactory;
import com.noob.rpc.serializer.Serializer;
import com.noob.rpc.serializer.SerializerFactory;
import com.noob.rpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 自定义服务代理类：当用户调用某个接口方法时，会改为调用invoke方法，在invoke方法中获取到要调用的方法信息、参数列表等，通过这些参数构造请求参数进而完成调用
 */
public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器 Serializer serializer = new JdkSerializer();
        Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();

        RpcRequest rpcRequest = RpcRequest.builder().serviceName(method.getDeclaringClass().getName()).methodName(method.getName()).parameterTypes(method.getParameterTypes()).args(args).build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            // ------- 服务节点选择 --------
            // 方式1：获取到第一个服务信息
            // ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);

            // 方式2：调用负载均衡算法选择一个服务节点
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String,Object> requestParams = new HashMap<>();
            requestParams.put("methodName",rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams,serviceMetaInfoList);

            // ------- 发送请求 --------
            // 发送请求方式1：http请求处理
//            return doHttpRequest(selectedServiceMetaInfo,bodyBytes);

            // 发送请求方式2：TCP请求处理
//            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest,selectedServiceMetaInfo);
//            return rpcResponse.getData();

            // 发送请求方式2：扩展实现：使用重试机制发送TCP请求
//            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
//            RpcResponse rpcResponse = retryStrategy.doRetry(()->
//                VertxTcpClient.doRequest(rpcRequest,selectedServiceMetaInfo)
//            );
//            return rpcResponse.getData();


            // 发送请求方式2：扩展实现：引入重试机制、容错机制发送TCP请求
            RpcResponse rpcResponse;
            try {
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() ->
                        VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
                );
            } catch (Exception e) {
                // 容错机制
                TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
                rpcResponse = tolerantStrategy.doTolerant(null, e);
            }
            return rpcResponse.getData();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Http请求处理
     *
     * @param selectedServiceMetaInfo
     * @param bodyBytes
     * @return
     * @throws IOException
     */
    private static RpcResponse doHttpRequest(ServiceMetaInfo selectedServiceMetaInfo, byte[] bodyBytes) throws IOException {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 发送 HTTP 请求
        try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress()).body(bodyBytes).execute()) {
            byte[] result = httpResponse.bodyBytes();
            // 反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return rpcResponse;
        }
    }
}
