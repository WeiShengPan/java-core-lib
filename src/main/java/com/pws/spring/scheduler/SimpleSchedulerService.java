package com.pws.spring.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Simple Scheduler. Enable by annotation @EnableScheduling
 * @author wilson.pan
 * @date 2021/3/1
 */
@Slf4j
@Component
public class SimpleSchedulerService {
    @Scheduled(cron = "0 * * * * ?")
    private void processCronExpression() {
        log.info("Scheduled task of cron expression: {}", new Date());
    }

    @Scheduled(fixedRate = 10000)
    private void processFixedRate() {
        log.info("Scheduled task of fixed rate : {}", new Date());
    }

    @Scheduled(fixedDelay = 5000)
    private void processFixedDelay() {
        log.info("Scheduled task of fixed delay : {}", new Date());
    }

    @Scheduled(initialDelay = 2000, fixedRate = 5000)
    private void processInitialDelay() {
        /*
         * Exactly one of the 'cron', 'fixedDelay(String)', or 'fixedRate(String)' attributes is required
         */
        log.info("Scheduled task of initial delay : {}", new Date());
    }
}
