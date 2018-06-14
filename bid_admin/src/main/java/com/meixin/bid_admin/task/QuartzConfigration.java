package com.meixin.bid_admin.task;

import org.quartz.*;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @Desc： 定时任务配置
 * @Author： yanghm
 * @Date： 17:05 2018/5/9 0009
 */
@Configuration
public class QuartzConfigration {

    private final Logger LOGGER = LoggerFactory.getLogger(QuartzConfigration.class);

    @Autowired
    private SpringJobFactory springJobFactory;

    @Autowired
    private SchedulerListener schedulerListener;

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    /**
     * attention:
     * Details：定义quartz调度工厂
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory() throws IOException, SchedulerException {
        SchedulerFactoryBean factory  = new SchedulerFactoryBean();
        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
//        factory .setOverwriteExistingJobs(true);
        // 延时启动，应用启动1秒后
//        factory .setStartupDelay(1);
        // 注册触发器
//        factory.setTriggers(cronJobTrigger);

        factory.setQuartzProperties(quartzProperties());
        factory.setSchedulerListeners(schedulerListener);
        factory.setJobFactory(springJobFactory);
        return factory;
    }



}
