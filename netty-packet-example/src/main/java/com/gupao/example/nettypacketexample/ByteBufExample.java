package com.gupao.example.nettypacketexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.internal.StringUtil;

public class ByteBufExample {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();//可自动扩容
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {//演示的时候，可以把循环的值扩大，就能看到扩容效果
            sb.append(" - " + i);
        }
        buf.writeBytes(sb.toString().getBytes());
        log(buf);
    }

    private static void log(ByteBuf buf) {
        StringBuilder builder = new StringBuilder()
                .append(" read index:").append(buf.readerIndex())//获取读索引
                .append(" write index:").append(buf.writerIndex()) //获取写索引
                .append(" capacity:").append(buf.capacity()) //获取容量
                .append(StringUtil.NEWLINE);
        //把ByteBuf中的内容，dump到StringBuilder中
        ByteBufUtil.appendPrettyHexDump(builder, buf);
        System.out.println(builder.toString());
    }
}
