package com.example.opcode;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
public enum OpCode {

    REQ((byte)0),
    RES((byte)1),
    PING((byte)2),
    PONG((byte)3);

    private byte code;

    OpCode(byte code){
        this.code=code;
    }

    public byte code(){
        return this.code;
    }

}
