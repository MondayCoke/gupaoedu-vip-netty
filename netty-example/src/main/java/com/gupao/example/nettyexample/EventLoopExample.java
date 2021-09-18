package com.gupao.example.nettyexample;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.ServerSocket;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class EventLoopExample {

    public static void main(String[] args) {
        EventLoopGroup group=new NioEventLoopGroup(2);
//        group.register(); //把某个channel注册到某一个EventLoop的Selector
        NioEventLoop eventExecutors=(NioEventLoop)group.next();
        ServerSocket ss=null;
//        System.out.println(group.next().register(ss));
        System.out.println(group.next());

        group.next().submit(()->{
            System.out.println(Thread.currentThread().getName()+"----");
        });
    }
}
