package com.network.mode.udp;

import java.io.IOException;
import java.net.*;

class UDPClient {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] arr = "Hello Server".getBytes();
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    DatagramSocket datagramSocket = new DatagramSocket();
                    DatagramPacket datagramPacket = new DatagramPacket(arr, arr.length, inetAddress, 1234);
                    datagramSocket.send(datagramPacket);
                    System.out.println("send end");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
