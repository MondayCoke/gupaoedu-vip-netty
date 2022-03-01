package com.gupaoedu.vip.netty.tomcat.nio.http;

import com.sun.deploy.net.HttpRequest;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class GPResponse {

    private ChannelHandlerContext ctx;
    private HttpRequest req;

    public GPResponse(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public void write(String s) throws Exception{

        if(null == s || 0 == s.length()){
            return;
        }

        try {
            FullHttpResponse response = new DefaultFullHttpResponse(
                    //设置HTTP版本号为1.1
                    HttpVersion.HTTP_1_1,
                    //设置返回HTTP状态码
                    HttpResponseStatus.OK,
                    //统一输出格式为UTF-8
                    Unpooled.wrappedBuffer(s.getBytes("UTF-8"))
            );

            response.headers().set("Content-Type", "text/html");

            ctx.write(response);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ctx.flush();
            ctx.close();
        }


    }

}
