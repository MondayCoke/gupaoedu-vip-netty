package com.gupaoedu.vip.netty.rpc.consumer;

import com.gupaoedu.vip.netty.rpc.api.IRpcHelloService;
import com.gupaoedu.vip.netty.rpc.api.IRpcService;
import com.gupaoedu.vip.netty.rpc.consumer.proxy.RpcProxy;
import com.gupaoedu.vip.netty.rpc.provider.RpcHelloServiceImpl;

public class RpcConsumer {
    public static void main(String[] args) {

        IRpcHelloService rpcHello = RpcProxy.create(IRpcHelloService.class);
        System.out.println(rpcHello.hello("Tom"));

        IRpcService rpc = RpcProxy.create(IRpcService.class);

        System.out.println("8 + 2 = " + rpc.add(8,2));
        System.out.println("8 - 2 = " + rpc.sub(8,2));
        System.out.println("8 * 2 = " + rpc.mult(8,2));
        System.out.println("8 / 2 = " + rpc.div(8,2));

    }
}
