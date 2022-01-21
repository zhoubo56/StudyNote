package com.imooc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
//@EnableScheduling
public class MyTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "*/5 * * * * ?")
    public void publishMsg() {
        logger.warn("start execute spring boot's task " + LocalDateTime.now());
    }
}
