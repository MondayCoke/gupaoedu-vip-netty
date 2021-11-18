package com.gupao.rpc.example.spring.reference;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 咕泡学院，只为更好的你
 * 咕泡学院-Mic: 2082233439
 * http://www.gupaoedu.com
 **/
@Slf4j
@Configuration
public class RpcReferenceAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }

    // 2、客户端启动加载配置
    @Bean
    public SpringRpcReferencePostProcessor postProcessor(){
        log.info("2、## SpringRpcReferencePostProcessor postProcessor");
        RpcClientProperties rc=new RpcClientProperties();
        rc.setRegistryAddress(this.environment.getProperty("gp.client.registryAddress"));
      /*  int port=Integer.parseInt();
        rc.setRegistryAddress(port);*/
        rc.setRegistryType(Byte.parseByte(this.environment.getProperty("gp.client.registryType")));
        return new SpringRpcReferencePostProcessor(rc);
    }
}
