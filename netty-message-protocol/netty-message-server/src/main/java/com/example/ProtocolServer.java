package com.example;

import com.example.codec.MessageRecordDecode;
import com.example.codec.MessageRecordEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class ProtocolServer {

    public static void main(String[] args) {
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup work=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class) //可能： 工厂方法通过反射构建实例
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //针对客户端连接来设置Pipeline
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder
                                        (1024*1024,
                                        9,
                                        4,
                                        0,
                                        0))
                                .addLast(new MessageRecordEncode())
                                .addLast(new MessageRecordDecode())
                                .addLast(new ServerHandler());
                    }
                });
        try {
            //CompletableFuture
            ChannelFuture channelFuture=bootstrap.bind(8080).sync();
            log.info("ProtocolServer start success {8080}");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
