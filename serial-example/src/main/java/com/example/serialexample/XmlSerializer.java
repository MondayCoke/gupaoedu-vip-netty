package com.example.serialexample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public class XmlSerializer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user=new User();
        user.setName("Mic");
        user.setAge(18);
        String xml=serialToFile(user);
        System.out.println(xml);
        System.out.println(xml.length());
        User nuser=deserialFromFile(xml);
        System.out.println(nuser);
    }
    private static String serialToFile(User user) throws IOException {
        return new XStream(new DomDriver()).toXML(user);
    }

    private static User deserialFromFile(String xml) throws IOException, ClassNotFoundException {
        return (User)new XStream(new DomDriver()).fromXML(xml);
    }
}
