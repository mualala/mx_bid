package com.meixin.bid_admin.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:12 2018/5/29 0029
 */
@Component
public class SystemDataInitializer implements ApplicationListener<ContextRefreshedEvent> {
    
    private final Logger LOGGER = LoggerFactory.getLogger(SystemDataInitializer.class);

    @Autowired
    private AdminDataInitializer dataInitializer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        dataInitializer.doInit();
    }
    
}
