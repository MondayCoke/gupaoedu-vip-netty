package com.example.serialexample;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class ProtobufSerializer {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        StudentProtos.Student student=StudentProtos.Student.newBuilder().setAge(-300).setName("Mic").build();
        byte[] bytes=student.toByteArray();
        System.out.println(bytes.length); //数据压缩
        //10(tag)   3(length)   [77   105   99](M i c)
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]+"   ");
        }
        System.out.println();
        StudentProtos.Student nstudent=StudentProtos.Student.parseFrom(bytes);
        System.out.println(nstudent);
    }
}
