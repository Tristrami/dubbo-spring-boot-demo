package com.seamew.dubbo.demo.consumer.task;

import com.seamew.dubbo.demo.api.GreetingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetingTask implements CommandLineRunner
{
    @Autowired
    private GreetingService greetingService;

    @Override
    public void run(String... args) throws Exception
    {
        new Thread(() -> {

            try {
                while (true) {
                    String greeting = greetingService.greeting("seamew");
                    log.info("Greeting! {}", greeting);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log.error("", e);
                Thread.currentThread().interrupt();
            }

        }, "greeting").start();
    }
}
