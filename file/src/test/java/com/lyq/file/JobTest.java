package com.lyq.file;

import com.lyq.file.task.ScheduleTask;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class JobTest extends BaseTest {

    @Test
    public void job() {
        // 任务
        JobDetail jobDetail = JobBuilder.newJob(ScheduleTask.class)
                .withIdentity("job1", "group1")
                .build();

        // 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "trigger1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();

        // 调度器
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail, trigger); // 放进去
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
