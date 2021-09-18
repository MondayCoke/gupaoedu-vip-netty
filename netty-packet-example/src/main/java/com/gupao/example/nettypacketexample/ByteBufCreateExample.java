package com.gupao.example.nettypacketexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ByteBufCreateExample {

    public static void main(String[] args) {
        //由JVM来管理内存
        ByteBuf buf=ByteBufAllocator.DEFAULT.heapBuffer(); //堆内存
        buf.writeBytes(new byte[]{1,2,3,4});
        log(buf);
        buf.writeInt(5);
        log(buf);
        System.out.println("开始进行读取操作");
        buf.markReaderIndex(); //标记索引位置.  markWriterIndex()
        byte b=buf.readByte();
        System.out.println(b);
        buf.resetReaderIndex(); //重新回到标记位置
        log(buf);
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
