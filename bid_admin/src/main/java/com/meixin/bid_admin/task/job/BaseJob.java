package com.meixin.bid_admin.task.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 15:38 2018/5/29 0029
 */
public abstract class BaseJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(BaseJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        String taskName = jobKey.getName();
        String group = jobKey.getGroup();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String name = dataMap.getString("bidName");
        Integer uid = dataMap.getInt("uid");

        int count = doService(name, uid, taskName, group);
        LOGGER.info("用户[{}] 竞标单 {}, taskName={} group={},更新了{}条记录", uid, name, taskName, group, count);
    }

    // 执行service逻辑
    abstract int doService(String name, int uid, String taskName, String groupId);

}
