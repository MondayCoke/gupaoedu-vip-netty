package com.gupao.rpc.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RpcClientProxy rcp=new RpcClientProxy();
        IUserService userService=rcp.clientProxy(IUserService.class,"127.0.0.1",8080);
        System.out.println(userService.saveUser("Mic"));
    }
}
