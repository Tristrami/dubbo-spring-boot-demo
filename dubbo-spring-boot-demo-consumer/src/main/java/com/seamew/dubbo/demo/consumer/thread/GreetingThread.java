package com.seamew.dubbo.demo.consumer.thread;

import com.seamew.dubbo.demo.api.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetingThread extends Thread
{
    @DubboReference
    private GreetingService greetingService;

    @DubboReference(version = "chinese")
    private GreetingService greetingServiceV2;

    public GreetingThread()
    {
        super("greeting");
    }

    @Override
    public void run()
    {
        try {
            while (true) {
                log.info("Greeting! {}", greetingService.greeting("seamew"));
                log.info("Greeting! {}", greetingServiceV2.greeting("seamew"));
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            log.error("", e);
            Thread.currentThread().interrupt();
        }
    }
}
