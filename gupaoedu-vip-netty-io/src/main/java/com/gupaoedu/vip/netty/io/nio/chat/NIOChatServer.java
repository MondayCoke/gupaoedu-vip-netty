package com.gupaoedu.vip.netty.io.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
    网络聊天室
    1、编写Java NIO服务端程序，支持多个客户端同时连接
    2、客户端初次连接时，服务端提示输入昵称，如果昵称有人占用，提示重新输入，如果昵称唯一，登陆成功
       按照一定的规则的组织数据（协议）（比如：昵称#@#你好！！)
    3、有新的客户端登录以后，群发欢迎信息同时统计在线人数，并且这些信息要通知到所有的在线客户
    4、服务器收到已登录客户端发送的内容，转发给其他所有在线的客户端
*/
public class NIOChatServer {

    private int port = 8080;

    private Charset charset = Charset.forName("UTF-8");

    //用HashSet记录在线人数及昵称
    private static HashSet<String> users = new HashSet<String>();

    //系统提示常量
    private static String USER_EXISTS = "系统提示：改昵称已经存在，请换一个昵称";

    //协议的分隔符
    private static String USER_CONTENT_SPLIT = "#@#";

    private Selector selector = null;

    public NIOChatServer(int port) throws IOException {

        this.port = port;

        ServerSocketChannel server = ServerSocketChannel.open();

        server.bind(new InetSocketAddress(this.port));

        server.configureBlocking(false);

        selector = Selector.open();

        server.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务已启动，监听端口是：" + this.port);

    }

    public static void main(String[] args) {
        try {
            new NIOChatServer(8080).listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException {

        while (true){
            int wait = selector.select();

            if(wait == 0) continue;

            Set<SelectionKey> keys = selector.selectedKeys();  //拿到所有连接到服务端用户
            Iterator<SelectionKey> iterator = keys.iterator();

            while (iterator.hasNext()){
                SelectionKey key =  (SelectionKey) iterator.next();
                iterator.remove();

                process(key);

            }
        }

    }

    private void process(SelectionKey key) throws IOException {

        if(key.isAcceptable()){
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();

            client.configureBlocking(false);

            client.register(selector,SelectionKey.OP_READ);

            key.interestOps(SelectionKey.OP_ACCEPT);

            client.write(charset.encode("请输入你的昵称"));
        }

        //如果key的状态变为能够处理来自客户端的数据
        if(key.isReadable()){
            SocketChannel client = (SocketChannel)key.channel();

            ByteBuffer buff = ByteBuffer.allocate(1024);

            StringBuilder content = new StringBuilder();
            try {
                while (client.read(buff) > 0) {
                    buff.flip();
                    content.append(charset.decode(buff));
                }

                key.interestOps(SelectionKey.OP_READ);
            }catch (IOException io){
                key.cancel();
                if(key.channel() != null){
                    key.channel().close();
                }
            }

            if(content.length() > 0){

                String[] onlineContent = content.toString().split(USER_CONTENT_SPLIT);

                if(onlineContent != null && onlineContent.length == 1){
                    String nickName = onlineContent[0];
                    if(users.contains(nickName)){
                        client.write(charset.encode(USER_EXISTS));
                    }else {
                        users.add(nickName);
                        int onlineCount = onlineCount();
                        String message = "欢迎 " + nickName + " 进入聊天室！当前在线人数：" + onlineCount;
                        broadCast(null,message);
                    }
                }
                else if(onlineContent != null && onlineContent.length > 1){
                    String nickName = onlineContent[0];
                    String message = content.substring(nickName.length() + USER_CONTENT_SPLIT.length());
                    message = nickName + " 说 " + message;
                    if(users.contains(nickName)){
                        broadCast(client,message);
                    }
                }

            }
        }

    }

    private void broadCast(SocketChannel client, String message) throws IOException {

        for (SelectionKey key : selector.keys()) {
           Channel targetChannel = key.channel();

           if(targetChannel instanceof SocketChannel && targetChannel != client){
               SocketChannel target = (SocketChannel)targetChannel;
               target.write(charset.encode(message));
           }
        }

    }

    private int onlineCount() {
        int res = 0;
        for (SelectionKey key : selector.keys()) {
            Channel target = key.channel();
            if(target instanceof SocketChannel){
                res ++;
            }
        }
        return res;
    }

}
