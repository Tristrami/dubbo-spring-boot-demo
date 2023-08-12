package com.seamew.dubbo.demo.consumer.task;

import com.seamew.dubbo.demo.consumer.thread.DeliveryThread;
import com.seamew.dubbo.demo.consumer.thread.GreetingThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestTask implements CommandLineRunner
{
    @Autowired
    private GreetingThread greetingThread;

    @Autowired
    private DeliveryThread deliveryThread;

    @Override
    public void run(String... args) throws Exception
    {
        greetingThread.start();
        deliveryThread.start();
    }
}
