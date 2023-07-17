package com.seamew.dubbo.demo.provider.service.impl;

import com.seamew.dubbo.demo.api.GreetingService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class GreetingServiceImpl implements GreetingService
{
    @Override
    public String greeting(String name)
    {
        return "Hola, " + name;
    }
}
