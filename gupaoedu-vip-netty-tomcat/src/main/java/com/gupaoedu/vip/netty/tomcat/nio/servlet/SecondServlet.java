package com.gupaoedu.vip.netty.tomcat.nio.servlet;

import com.gupaoedu.vip.netty.tomcat.nio.http.GPRequest;
import com.gupaoedu.vip.netty.tomcat.nio.http.GPResponse;
import com.gupaoedu.vip.netty.tomcat.nio.http.GPServlet;

public class SecondServlet extends GPServlet {
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        this.doPost(request,response);
    }

    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("This is second servlet from NIO.");
    }
}
