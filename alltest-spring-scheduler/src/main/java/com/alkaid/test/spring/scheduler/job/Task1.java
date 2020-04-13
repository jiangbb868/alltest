package com.alkaid.test.spring.scheduler.job;

import java.util.Date;

public class Task1 implements Runnable {

    @Override
    public void run() {
        System.out.println(new Date());
    }
}
