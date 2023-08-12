package com.seamew.dubbo.demo.provider.service.impl;

import com.seamew.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(registry = "ningbo", version = "chinese")
public class GreetingServiceImplV2 implements GreetingService
{
    @Override
    public String greeting(String name)
    {
        return "你好，" + name;
    }
}
