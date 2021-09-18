package com.example.codec;

import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class MessageRecordDecode extends ByteToMessageDecoder {

    /**
     * sessionId | reqType | Content-length | Content
     *
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        MessageRecord record=new MessageRecord();
        //ByteBuf 表示接收到的消息报文
        Header header=new Header();
        header.setSessionId(in.readLong()); //读取8个字节
        header.setReqType(in.readByte()); //消息类型
        header.setLength(in.readInt()); //读取四个字节长度，消息内容长度
        record.setHeader(header);
        if(header.getLength()>0){
            byte[] contents=new byte[header.getLength()];
            in.readBytes(contents);
            /**
             * Java原生的对象流
             */
            ByteArrayInputStream bis=new ByteArrayInputStream(contents);
            ObjectInputStream ios=new ObjectInputStream(bis);
            record.setBody(ios.readObject());//得到一个反序列化的数据
            log.info("反序列化出来的结果："+record);
            out.add(record);
        }else{
            log.info("消息内容为空");
        }
    }
}
