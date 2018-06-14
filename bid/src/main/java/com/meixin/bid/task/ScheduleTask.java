package com.meixin.bid.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 16:58 2018/5/9 0009
 */
@Configuration
@Component
@EnableScheduling
public class ScheduleTask {

    private final Logger LOGGER = LoggerFactory.getLogger(ScheduleTask.class);

    public void sayHello() {
        LOGGER.info("Hello, my task is print time,haha!! "+System.currentTimeMillis());
    }

}
