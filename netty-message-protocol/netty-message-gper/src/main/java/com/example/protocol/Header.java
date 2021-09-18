package com.example.protocol;

import lombok.Data;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class Header {

    private long sessionId;  //会话id，8个字节
    private byte reqType;  //消息类型， 1个字节
    private int length; //消息体的长度  4个字节
}
