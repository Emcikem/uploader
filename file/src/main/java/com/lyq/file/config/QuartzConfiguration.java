package com.lyq.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    /**
     * 配置Scheduler
     */
    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // Scheduler自动开始
        schedulerFactoryBean.setAutoStartup(true);

        return schedulerFactoryBean;
    }
}
