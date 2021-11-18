package com.gupao.rpc.example.protocol;

import com.gupao.rpc.example.IRegistryService;
import com.gupao.rpc.example.ServiceInfo;
import com.gupao.rpc.example.core.RpcProtocol;
import com.gupao.rpc.example.core.RpcRequest;
import com.gupao.rpc.example.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
    /*private String serviceAddress;
    private int servicePort;*/

    public NettyClient() {
        log.info("8、 begin init Netty Client！");
        bootstrap=new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

       /* this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;*/
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol, IRegistryService registryService) throws Exception {
        log.info("9、NettyClient 的 sendRequest");
        ServiceInfo serviceInfo=registryService.discovery(protocol.getContent().getClassName());
        final ChannelFuture future=bootstrap.connect(serviceInfo.getServiceAddress(),serviceInfo.getServicePort()).sync();
        future.addListener(listener->{
            if(future.isSuccess()){
                log.info("连接rpc服务成功了！ connect rpc server {} success.",serviceInfo.getServiceAddress());
            }else{
                log.error("connect rpc server {} failed. ",serviceInfo.getServiceAddress());
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("传输数据开始了！ begin transfer data");
        future.channel().writeAndFlush(protocol);
    }
}
