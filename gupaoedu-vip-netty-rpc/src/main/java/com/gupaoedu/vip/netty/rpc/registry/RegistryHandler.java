package com.gupaoedu.vip.netty.rpc.registry;

import com.gupaoedu.vip.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //注册中心容器
    private static ConcurrentHashMap<String,Object> registryMap = new ConcurrentHashMap<String, Object>();

    private List<String> classNames = new ArrayList<String>();

    public RegistryHandler(){
        
        //扫描所有需要注册的类
        //com.gupaoedu.vip.netty.rpc.provider
        scannerClass("com.gupaoedu.vip.netty.rpc.provider");
        
        //将扫描到的类注册到一个容器中
        doRegister();
    }

    private void doRegister() {
        if(classNames.size() == 0){return;}

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> i = clazz.getInterfaces()[0];
                registryMap.put(i.getName(),clazz.newInstance());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.","/"));
        File dir = new File(url.getFile());

        for (File file : dir.listFiles()) {

            //如果是文件夹，继续递归
            if(file.isDirectory()){
                scannerClass(packageName + "." + file.getName());
            }else {
                classNames.add(packageName + "." + file.getName().replace(".class","").trim());
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol request = (InvokerProtocol) msg;

        if(registryMap.containsKey(request.getClassName())){
            //用反射直接调用Provider的方法
            Object provider = registryMap.get(request.getClassName());
            Method method = provider.getClass().getMethod(request.getMethodName(),request.getParams());
            result = method.invoke(provider,request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }
}
