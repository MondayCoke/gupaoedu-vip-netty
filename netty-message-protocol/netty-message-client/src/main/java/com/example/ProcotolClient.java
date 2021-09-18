package com.example;

import com.example.codec.MessageRecordDecode;
import com.example.codec.MessageRecordEncode;
import com.example.opcode.OpCode;
import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ProcotolClient {

    public static void main(String[] args) {
        EventLoopGroup worker=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(worker).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1024,
                                9,
                                4,
                                0,
                                0))
                        .addLast(new MessageRecordEncode())
                        .addLast(new MessageRecordDecode())
                        .addLast(new ClientHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8080)).sync();
            Channel channel=future.channel();
            for (int i = 0; i < 100; i++) {
                MessageRecord record=new MessageRecord();
                Header header=new Header();
                header.setSessionId(100001);
                header.setReqType(OpCode.REQ.code());
                record.setHeader(header);
                String body="我是请求数据:"+i;
                record.setBody(body);
                channel.writeAndFlush(record);
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
        }
    }
}
