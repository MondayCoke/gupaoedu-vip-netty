package com.example.protocol;

import lombok.Data;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Data
public class MessageRecord {
    private Header header;
    private Object body;
}
