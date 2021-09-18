package com.example.serialexample;

import com.alibaba.fastjson.JSON;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class HessianSerializer {

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
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        HessianOutput ho=new HessianOutput(baos);
        ho.writeObject(user);
        return baos.toByteArray();

    }

    private static User deserialFromFile(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis=new ByteArrayInputStream(data);
        HessianInput hi=new HessianInput(bis);
        return (User)hi.readObject();
    }
}
