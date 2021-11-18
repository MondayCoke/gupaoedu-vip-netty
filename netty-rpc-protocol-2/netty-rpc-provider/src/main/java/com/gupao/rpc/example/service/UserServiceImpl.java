package com.gupao.rpc.example.service;

import com.gupao.rpc.example.IUserService;
import com.gupao.rpc.example.annotation.GpRemoteService;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/

@GpRemoteService
@Slf4j
public class UserServiceImpl implements IUserService {
    @Override
    public String saveUser(String name) {
        log.info("调到了rpc服务的方法！");
        log.info("begin save user:{}",name);
        return "save User success: "+name;
    }
}
