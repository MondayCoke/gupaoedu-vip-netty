package com.gupaoedu.vip.netty.tomcat.bio.servlet;

import com.gupaoedu.vip.netty.tomcat.bio.http.GPRequest;
import com.gupaoedu.vip.netty.tomcat.bio.http.GPResponse;
import com.gupaoedu.vip.netty.tomcat.bio.http.GPServlet;

public class MyServlet extends GPServlet {
    public void doGet(GPRequest request, GPResponse response) throws Exception {
        this.doPost(request,response);
    }

    public void doPost(GPRequest request, GPResponse response) throws Exception {
        response.write("This is my servlet from BIO.");
    }
}
