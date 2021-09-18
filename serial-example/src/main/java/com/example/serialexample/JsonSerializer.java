package com.example.serialexample;

import com.alibaba.fastjson.JSON;

import java.io.*;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class JsonSerializer {

    //Jackson
    //FastJson
    //Gson
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user=new User();
        user.setName("Mic");
        user.setAge(18);
        String json= serialToFile(user);
        System.out.println(json);
        System.out.println(json.length());
        User nuser=deserialFromFile(json);
        System.out.println(nuser);
    }
    private static String serialToFile(User user) throws IOException {
        return JSON.toJSONString(user);
    }

    private static User deserialFromFile(String json) throws IOException, ClassNotFoundException {
        return JSON.parseObject(json,User.class);
    }
}
