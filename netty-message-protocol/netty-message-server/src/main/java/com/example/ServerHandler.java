package com.example;

import com.example.opcode.OpCode;
import com.example.protocol.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.LazyReflectiveObjectGenerator;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ServerHandler， 应该是SocketChannel触发的。
        MessageRecord record=(MessageRecord)msg;
        log.info("Server Receive Message:"+record);
        record.setBody("Server Response Message");
        record.getHeader().setReqType(OpCode.RES.code());
        ctx.writeAndFlush(record);
//        super.channelRead(ctx, msg);
    }
}
