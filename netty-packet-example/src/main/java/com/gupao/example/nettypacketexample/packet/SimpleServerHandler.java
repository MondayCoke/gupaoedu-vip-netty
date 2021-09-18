package com.gupao.example.nettypacketexample.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*ByteBuf in=(ByteBuf) msg;
        byte[] req=new byte[in.readableBytes()];
        in.readBytes(req); //把数据读到byte数组中
        String body=new String(req,"UTF-8");*/
        System.out.println("服务器端收到消息："+msg);
        //写回数据
       /* ByteBuf resp= Unpooled.copiedBuffer(UUID.randomUUID().toString().getBytes());
        ctx.writeAndFlush(resp);*/
    }
}
