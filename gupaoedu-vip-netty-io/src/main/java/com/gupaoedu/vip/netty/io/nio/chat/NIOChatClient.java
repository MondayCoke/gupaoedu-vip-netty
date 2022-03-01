package com.gupaoedu.vip.netty.io.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOChatClient {

    private final InetSocketAddress serverAdreess = new InetSocketAddress("localhost",8080);

    private Selector selector = null;
    private SocketChannel client = null;

    private String nickName = "";
    private Charset charset = Charset.forName("UTF-8");

    //系统提示常量
    private static String USER_EXISTS = "系统提示：改昵称已经存在，请换一个昵称";

    //协议的分隔符
    private static String USER_CONTENT_SPLIT = "#@#";

    public NIOChatClient() throws IOException{
        selector = Selector.open();

        client = SocketChannel.open(serverAdreess);
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        new NIOChatClient().session();
    }

    private void session() {
        //从服务器读数据的线程
        new Reader().start();
        //往服务器写数据的线程
        new Writer().start();
    }

    private class Writer extends Thread {
        @Override
        public void run() {
            try {
                Scanner scan = new Scanner(System.in);
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    if ("".equals(line)) continue;
                    //第一次登录保存用户昵称
                    if ("".equals(nickName)) {
                        nickName = line;
                        line = nickName + USER_CONTENT_SPLIT;
                    } else {
                        line = nickName + USER_CONTENT_SPLIT + line;
                    }
                    client.write(charset.encode(line));
                }
                scan.close();
            }catch (Exception e){

            }

        }
    }

    private class Reader extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    int readyChannels = selector.select();
                    if(readyChannels == 0) continue;
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                    while (keyIterator.hasNext()){
                        SelectionKey key = (SelectionKey) keyIterator.next();
                        keyIterator.remove();
                        process(key);
                    }
                }
            }catch (Exception e){

            }

        }

        private void process(SelectionKey key) throws IOException {
            if(key.isReadable()){

                SocketChannel sc = (SocketChannel) key.channel();

                ByteBuffer buff = ByteBuffer.allocate(1024);
                String content = "";

                while (sc.read(buff) > 0){
                    buff.flip();
                    content += charset.decode(buff);
                }

                if(USER_EXISTS.equals(content)){
                    nickName = "";
                }
                System.out.println(content);
                key.interestOps(SelectionKey.OP_READ);
            }
        }
    }
}
