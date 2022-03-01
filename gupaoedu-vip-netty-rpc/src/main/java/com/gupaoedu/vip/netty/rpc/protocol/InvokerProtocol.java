package com.gupaoedu.vip.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvokerProtocol implements Serializable {

    private String className;  //类名
    private String methodName; //方法名称
    private Class<?>[] params; //形参列表
    private Object[] values;   //实参列表

}
