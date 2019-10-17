package com.alkaid.alltest.quartz;


import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;

public class QuartzApplication {


    public static void main(String[] args) throws SchedulerException {
        SchedulerManager schedulerManager = new SchedulerManager();
        schedulerManager.start();

       JobDetail jobDetail = newJob(SimpleJobA.class)
               .withIdentity("job_A", schedulerManager.getSchedulerId())
               .requestRecovery()
               .build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("triger_A", schedulerManager.getSchedulerId())
                .startAt(futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(simpleSchedule().withRepeatCount(20).withIntervalInSeconds(5)).build();
        schedulerManager.addJob(jobDetail, trigger);
        try {
                Thread.sleep(60 * 1000L);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        schedulerManager.shutdown();
    }
}
