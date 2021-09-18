package com.example.serialexample;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class AvroSerializer {

    public static void main(String[] args) throws IOException {
        Person person=Person.newBuilder().setAge(18).setName("Mic").build();

        ByteBuffer byteBuffer=person.toByteBuffer();
        System.out.println("序列化大小："+byteBuffer.array().length);
        Person nperson=Person.fromByteBuffer(byteBuffer);
        System.out.println("反序列化："+nperson);
    }
}
