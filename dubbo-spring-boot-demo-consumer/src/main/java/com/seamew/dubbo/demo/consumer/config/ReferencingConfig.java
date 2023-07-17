package com.seamew.dubbo.demo.consumer.config;

import com.seamew.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferencingConfig
{
    @Bean
    @DubboReference(interfaceClass = GreetingService.class)
    public ReferenceBean<GreetingService> greetingService()
    {
        return new ReferenceBean<>();
    }
}
