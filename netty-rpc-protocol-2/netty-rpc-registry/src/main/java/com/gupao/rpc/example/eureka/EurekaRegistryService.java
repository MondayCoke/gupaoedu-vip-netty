package com.gupao.rpc.example.eureka;

import com.gupao.rpc.example.IRegistryService;
import com.gupao.rpc.example.ServiceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class EurekaRegistryService implements IRegistryService {


    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {

    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        return null;
    }
}
