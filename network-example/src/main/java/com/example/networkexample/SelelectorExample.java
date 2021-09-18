package com.example.networkexample;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class SelelectorExample implements Runnable{

     Selector selector;
    public static void main(String[] args) {
        //NIO


    }

    @Override
    public void run() {
        while(true){
            try {
                selector.select();//阻塞方法
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                SocketChannel socketChannel=(SocketChannel) selectionKeys.iterator().next().channel();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
