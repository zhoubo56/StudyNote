package com.imooc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableAsync
public class MyAsyncTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public void publishMsg() {
        try {
            Thread.sleep(5000);
            logger.warn("start execute spring async task " + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
