package com.example.serialexample;

import lombok.Data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class User implements Serializable {
    //校验对象是否发生了变化
//    private static final long serialVersionUID = 2430403711774937480L;

//    private static final long serialVersionUID = -1818480343277142618L;

    private String name;

    private int age;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.name=(String)in.readObject();
    }
}
