package com.seamew.dubbo.demo.provider.service.impl;

import com.seamew.dubbo.demo.api.DeliveryService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(registry = "ningbo", version = "ningbo")
public class NingboDeliveryServiceImpl implements DeliveryService
{
    @Override
    public String deliver(String item)
    {
        return "Deliver " + item + " to Ningbo";
    }
}
