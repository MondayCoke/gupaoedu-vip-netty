package com.example.serialexample;

import java.io.*;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class JavaSerializer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user=new User();
        user.setName("Mic");
        user.setAge(18);
        byte[] bytes=serialToFile(user);
        System.out.println(bytes.length);
        User nuser=deserialFromFile(bytes);
        System.out.println(nuser);
    }
    private static byte[] serialToFile(User user) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(user);
        return bos.toByteArray();
    }

    private static <T> T deserialFromFile(byte[] data) throws IOException, ClassNotFoundException {
        ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(data));
        return (T)ois.readObject();
    }
}
