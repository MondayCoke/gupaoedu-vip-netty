package com.example.codec;

import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class MessageRecordEncode extends MessageToByteEncoder<MessageRecord> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageRecord msg, ByteBuf out) throws Exception {
        log.info("==========开始进行消息编码==================");
        Header header=msg.getHeader(); //得到消息协议的header部分
        out.writeLong(header.getSessionId()); //写8个字节的sessionid
        out.writeByte(header.getReqType()); //1个字节的消息类型

        Object body=msg.getBody();
        if(body!=null){
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(bos);
            oos.writeObject(body);
            byte[] bytes= bos.toByteArray();//
            out.writeInt(bytes.length); //写消息的长度
            out.writeBytes(bytes);
        }else{
            out.writeInt(0); //表示消息长度为0
        }
    }
}
