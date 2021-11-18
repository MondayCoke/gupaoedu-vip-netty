package com.gupao.rpc.example.spring.service;

import com.gupao.rpc.example.IRegistryService;
import com.gupao.rpc.example.RegistryFactory;
import com.gupao.rpc.example.RegistryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    // 2、根据 rpcServerProperties 配置，开始注册
    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) throws UnknownHostException {
        log.info("## RpcProviderAutoConfiguration springRpcProviderBean");
        IRegistryService registryService =
                RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(),
                        RegistryType.findByCode(rpcServerProperties.getRegistryType()));
        // 6、初始化 SpringRpcProviderBean
        return new SpringRpcProviderBean(rpcServerProperties.getServicePort(), registryService);
    }
}
