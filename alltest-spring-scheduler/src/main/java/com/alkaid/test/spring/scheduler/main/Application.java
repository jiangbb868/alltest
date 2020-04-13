package com.alkaid.test.spring.scheduler.main;

import com.alkaid.test.spring.scheduler.job.Task1;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.concurrent.ScheduledFuture;

public class Application {

    public static void main(String[] argv) {
        testSpringScheduler();
    }

    private static void testSpringScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        scheduler.setPoolSize(20);
        scheduler.setThreadNamePrefix("spring-task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        ScheduledFuture<?> future = scheduler.schedule(new Task1(), new CronTrigger("*/5 * * * * *"));
    }

    private static void testCron4j() {

    }
}
