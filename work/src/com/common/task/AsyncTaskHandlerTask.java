package com.common.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@EnableScheduling
public class AsyncTaskHandlerTask {

    Logger logger = Logger.getLogger(AsyncTaskHandlerTask.class);
    @Scheduled(fixedDelay = 1000)
    public void task1() {
        logger.info("task1");
    }

    @Scheduled(fixedDelay = 1000)
    public void task2() {
        logger.info("task2");
    }
}