package com.gupao.example.nettyexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class NormalMessageHandler extends ChannelInboundHandlerAdapter {

    //channelReadComplete方法表示消息读完了的处理，writeAndFlush方法表示写入并发送消息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //这里的逻辑就是所有的消息读取完毕了，在统一写回到客户端。
        // Unpooled.EMPTY_BUFFER表 示空消息，addListener(ChannelFutureListener.CLOSE)表示写完后，就关闭连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    //channelRead方法表示读到消息以后如何处理，这里我们把消息打印出来
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);//把数据读到byte数组中
        System.out.println("服务端收到的数据：" + new String(req, "utf-8"));
        //写回数据
        ByteBuf resp = Unpooled.copiedBuffer(("receive message:" + new String(req, "utf-8") + "").getBytes());

        ctx.write(resp);
        //ctx.write表示把消息再发送回客户端，但是仅仅是写到缓冲区，没有发送，flush才会真正写 到网络上去
        super.channelRead(ctx, msg);
    }

    //exceptionCaught方法就是发生异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
