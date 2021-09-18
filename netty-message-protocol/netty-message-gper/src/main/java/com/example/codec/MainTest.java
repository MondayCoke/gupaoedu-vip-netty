package com.example.codec;

import com.example.codec.MessageRecordDecode;
import com.example.codec.MessageRecordEncode;
import com.example.opcode.OpCode;
import com.example.protocol.Header;
import com.example.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Deprecated
public class MainTest {

    public static void main(String[] args) throws Exception {
        //ChannelHandler单元测试
        EmbeddedChannel channel=new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder
                        (1024*1024,
                                9,
                                4,
                                0,
                                0),
                new LoggingHandler(),
                new MessageRecordEncode(),
                new MessageRecordDecode()
        );
        //定义消息内容
        Header header=new Header();
        header.setSessionId(1234546);
        header.setReqType(OpCode.REQ.code());
        MessageRecord record=new MessageRecord();
        record.setHeader(header);
        record.setBody("Hello World");

//        channel.writeOutbound(record) ;//写出去，编码

       /* ByteBuf buf= ByteBufAllocator.DEFAULT.buffer();
        new MessageRecordEncode().encode(null,record,buf);

        channel.writeInbound(buf); //读取消息内容，进行解码*/

        //编码得到了一个Bytebuf
        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer();
        new MessageRecordEncode().encode(null,record,buf);

        ByteBuf bb1=buf.slice(0,7); //数据包
        ByteBuf bb2=buf.slice(7,buf.readableBytes()-7); //数据包2
        bb1.retain();

        channel.writeInbound(bb1);
        channel.writeInbound(bb2);
    }
}
