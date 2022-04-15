package com.lyq.file.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Date;


@EnableScheduling
@Component
public class ScheduleTask implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("MyJob execute" + new Date());
//        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
//        System.out.println(map.get("name") + " Hello, I'm the quartz job!");
    }
}
