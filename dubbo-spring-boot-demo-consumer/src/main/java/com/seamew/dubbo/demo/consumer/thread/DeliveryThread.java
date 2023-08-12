package com.seamew.dubbo.demo.consumer.thread;

import com.seamew.dubbo.demo.api.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeliveryThread extends Thread
{
    @DubboReference(registry = "hangzhou", version = "hangzhou")
    private DeliveryService hangzhouDeliveryService;

    @DubboReference(registry = "ningbo", version = "ningbo")
    private DeliveryService ningboDeliveryService;

    @Override
    public void run()
    {
        while (true) {
            log.info("{}", hangzhouDeliveryService.deliver("lulu"));
            log.info("{}", ningboDeliveryService.deliver("zhuzhu"));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
