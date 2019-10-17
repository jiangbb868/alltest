package com.alkaid.alltest.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobB implements Job {

    Logger logger = LoggerFactory.getLogger(SimpleJobA.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Simple Job B execute ...");
    }
}
