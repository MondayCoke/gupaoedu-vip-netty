package com.gupao.example.nettypacketexample.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class PacketNettyClient {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                //表示传输消息的时候，在消息报文中增加4个字节的length。->发送的ByteBuf
                                .addLast(new LengthFieldPrepender(4,0,false))
//                                .addLast(new FixedLengthFrameDecoder(36))
//                                .addLast(new DelimiterBasedFrameDecoder
//                                        (10,true,true,delimiter))
                                .addLast(new StringEncoder())
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {

                                        ctx.writeAndFlush("i am first request");
                                        ctx.writeAndFlush("i am second request");
//                                        super.channelActive(ctx);
                                    }
                                });
                    }
                });
        try {
            ChannelFuture future=bootstrap.connect("localhost",8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
