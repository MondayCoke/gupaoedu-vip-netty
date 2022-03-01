package com.gupaoedu.vip.netty.tomcat.bio.servlet;

import com.gupaoedu.vip.netty.tomcat.bio.http.GPRequest;
import com.gupaoedu.vip.netty.tomcat.bio.http.GPResponse;
import com.gupaoedu.vip.netty.tomcat.bio.http.GPServlet;

public class SecondServlet extends GPServlet {
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        this.doPost(request,response);
    }

    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("This is second servlet from BIO.");
    }
}
