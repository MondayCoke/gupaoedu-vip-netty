package com.gupao.rpc.example.loadbalance;

import com.gupao.rpc.example.ServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
public class RandomLoadBalance extends AbstractLoadBalance{


    @Override
    protected ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers) {
        log.info("RandomLoadBalance, 开始负载均衡！");
        int len= servers.size();
        Random random=new Random();
        return servers.get(random.nextInt(len));
    }
}
