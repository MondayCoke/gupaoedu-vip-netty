package com.gupaoedu.vip.netty.tomcat.bio.http;

import java.io.OutputStream;

public class GPResponse {
    private OutputStream out;

    public GPResponse(OutputStream os) {
        this.out = os;
    }

    public void write(String s) throws Exception{
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 ok\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }

}
