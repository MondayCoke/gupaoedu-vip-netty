package com.example.networkexample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ServerSocketExample {

    public static void main(String[] args) throws IOException {
        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT);
        System.out.println("启动服务，监听端口：" + DEFAULT_PORT);
        while (true) {
            Socket socket = serverSocket.accept(); //阻塞式通信
            System.out.println("客户端：" + socket.getPort() + "已连接");
            new Thread(new Runnable() {
                Socket socket;
                public Runnable setSocket(Socket s){
                    this.socket=s;
                    return this;
                }
                @Override
                public void run() {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String clientStr = null; //读取一行信息
                        //如果客户端没有写数据过来，此时服务端处于阻塞状态
                        clientStr = bufferedReader.readLine(); //poll()
                        System.out.println("客户端发了一段消息：" + clientStr);
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write("我已经收到你的消息了");
                        bufferedWriter.flush(); //清空缓冲区触发消息发送
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.setSocket(socket)).start();

        }
    }
}
