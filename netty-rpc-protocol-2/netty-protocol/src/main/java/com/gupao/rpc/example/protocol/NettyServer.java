package com.gupao.rpc.example.protocol;

import com.gupao.rpc.example.handler.RpcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class NettyServer {

    private String serverAddress; //服务地址
    private int serverPort; //端口

    public NettyServer(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    // 8、开启新线程 启动 NettyServer
    public void startNettyServer(){
        log.info("begin start Netty server");

        EventLoopGroup boss=new NioEventLoopGroup(); //主线程
        EventLoopGroup worker=new NioEventLoopGroup();// 表示工作线程组(register)

        ServerBootstrap bootstrap=new ServerBootstrap();  //构建Netty Server的API
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class) //指定epoll模型
                .childHandler(new RpcServerInitializer()); //具体的工作处理类,负责处理相关SocketChannel的IO就绪事件
        try {
            ChannelFuture future=bootstrap.bind(this.serverAddress,this.serverPort).sync();
            log.info("11、 Server started Success on Port,{}",this.serverPort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
