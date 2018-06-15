package com.meixin.bid_admin.task;

import com.meixin.bid_admin.task.job.EndBiddingJob;
import com.meixin.bid_admin.task.job.StartBiddingJob;
import com.meixin.bid_admin.web.support.Utils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:33 2018/5/29 0029
 */
public class BiddingTaskUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BiddingTaskUtil.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private enum TaskType {
        START_MARK("START-"), // 开启
        END_MARK("END-");     // 结束

        private String mark;

        TaskType(String mark) {
            this.mark = mark;
        }

        public String getMark() {
            return mark;
        }

        //设置task的name
        public String taskName(String name) {
            return getMark().concat(name);
        }

    }

    public static void startBiddingTask(Scheduler scheduler, String name, String group, String time) {
        setBiddingTask(scheduler, name, group, time, TaskType.START_MARK);
    }

    public static void endBiddingTask(Scheduler scheduler, String name, String group, String time) {
        setBiddingTask(scheduler, name, group, time, TaskType.END_MARK);
    }

    private static void setBiddingTask(Scheduler scheduler, String name, String group, String time, TaskType taskType) {
        try {
            Class jobClazz = null;
            switch (taskType) {
                case START_MARK: jobClazz = StartBiddingJob.class; break;
                case END_MARK: jobClazz = EndBiddingJob.class; break;
            }

            if (jobClazz != null) {
                String taskName = taskType.taskName(name);
                String groupId = Utils.ID.taskGroupId(group);

                JobDataMap dataMap = new JobDataMap();
                dataMap.put("bidName", name);
                dataMap.put("uid", group);

                JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                        .withIdentity(taskName, groupId)
                        .setJobData(dataMap)
                        .build();

                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Date date = sdf.parse(time);

                Trigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(taskName, groupId)
                        .startAt(date)
//                .endAt(endDate)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().
//                        withIntervalInSeconds(2).repeatForever())
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);
            }else {

            }
        }catch (ParseException e) {
            LOGGER.error("创建竞标单定时器失败,日期格式解析异常 {}", e);
        } catch (SchedulerException e) {
            LOGGER.error("创建竞标单定时器失败,scheduler异常 {}", e);
        }

    }


}
