package com.seamew.dubbo.demo.provider.service.impl;

import com.seamew.dubbo.demo.api.DeliveryService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(registry = "hangzhou", version = "hangzhou")
public class HangzhouDeliveryServiceImpl implements DeliveryService
{
    @Override
    public String deliver(String item)
    {
        return "Deliver " + item + " to Hangzhou";
    }
}
