package com.gupao.example.nettypacketexample;

import io.netty.buffer.*;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class CompositeByteBufExample {

    public static void main(String[] args) {
        ByteBuf header= ByteBufAllocator.DEFAULT.buffer();
        header.writeBytes(new byte[]{1,2,3,4,5});
        ByteBuf body=ByteBufAllocator.DEFAULT.buffer();
        body.writeBytes(new byte[]{6,7,8,9,10});

        ByteBuf total= Unpooled.buffer(header.readableBytes()+body.readableBytes());
        total.writeBytes(header);
        total.writeBytes(body);
        //从逻辑成面构建了一个总的buf数据。
        //第二个零拷贝实现
        CompositeByteBuf compositeByteBuf=Unpooled.compositeBuffer();
        compositeByteBuf.addComponents(true,header,body);
        log(compositeByteBuf);


        //Unpooled
//        ByteBuf total=Unpooled.wrappedBuffer(header,body);
        log(total);
        header.setByte(2,9);
        log(total);
    }
    private static void log(ByteBuf buf){
        StringBuilder sb=new StringBuilder();
        sb.append(" read index:").append(buf.readerIndex());  //读索引
        sb.append(" write index:").append(buf.writerIndex()); //写索引
        sb.append(" capacity :").append(buf.capacity()) ; //容量
        ByteBufUtil.appendPrettyHexDump(sb,buf);
        System.out.println(sb.toString());
    }
}
