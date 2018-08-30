package com.meixin.bid_admin.task;

import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.mappers.dao.BiddingDao;
import com.meixin.bid_admin.task.job.EndBiddingJob;
import com.meixin.bid_admin.task.job.StartBiddingJob;
import com.meixin.bid_admin.web.support.Utils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:33 2018/5/29 0029
 */
@Component
public class BiddingTaskUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BiddingTaskUtil.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static String groupName;

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
            String realTaskName = (groupName != null) ? (groupName + "-") : "";
            return realTaskName.concat(getMark().concat(name));
        }

    }

    public static void startBiddingTask(Scheduler scheduler, String name, String group, String time, BiddingDao biddingDao) {
        setBiddingTask(scheduler, name, group, time, TaskType.START_MARK, biddingDao);
    }

    public static void endBiddingTask(Scheduler scheduler, String name, String group, String time, BiddingDao biddingDao) {
        setBiddingTask(scheduler, name, group, time, TaskType.END_MARK, biddingDao);
    }

    private static String fmtGroupId(String groupId) {
        return (groupName != null) ? (groupName + "-" + groupId) : groupId;
    }

    //开启定时器
    private static void setBiddingTask(Scheduler scheduler, String name, String group, String time, TaskType taskType, BiddingDao biddingDao) {
        String taskName = taskType.taskName(name);
        String groupId = Utils.ID.taskGroupId(fmtGroupId(group));
        try {
            Class jobClazz = null;
            switch (taskType) {
                case START_MARK: jobClazz = StartBiddingJob.class; break;
                case END_MARK: {
                    jobClazz = EndBiddingJob.class;
                    //设置end task的trigger的name和groupId到数据库
                    biddingDao.setTaskInfo(name, Integer.valueOf(group), taskName, groupId);
                    break;
                }
            }

            if (jobClazz != null) {
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
            LOGGER.error("日期格式解析异常,竞标单定时器错误,定时器类型[{}],竞标单名称[{}]", taskType.getMark(), name);
        } catch (SchedulerException e) {
            LOGGER.error("scheduler异常{},竞标单定时器错误,定时器类型[{}],竞标单名称[{}]", e, taskType.getMark(), name);
        }

    }


    /**
     * @Desc:   按新的trigger重新设置job执行
     * @Author: yanghm
     * @Param:
     * @Date:   13:30 2018/6/22 0022
     * @Return: delay time 延迟后的结束时间
     */
    public static Map<String, Object> rescheduleBidding(Scheduler scheduler, Bidding bidding, long addDelay) throws SchedulerException {
        String taskName = bidding.getTaskName();
        String groupId = bidding.getGroupId();

        // 计算新的结束时间
        /*
        long delay = Utils.Delay.getDelay();//追加的时间
        long endTime = bidding.getEndTime().getTime();

        long delayTime = endTime + delay;//追加后的总延时时间
        */
        long delayTime = bidding.getEndTime().getTime() + addDelay;
        Date delayDate = new Date(delayTime);

        //gov doc: http://www.quartz-scheduler.org/documentation/quartz-2.2.x/cookbook/UpdateTrigger.html
        TriggerKey oldTriggerKey = TriggerKey.triggerKey(taskName, groupId);

        String uid = String.valueOf(bidding.getUid());
        String newGroupId = Utils.ID.taskGroupId(fmtGroupId(uid));
        Trigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity(taskName, newGroupId)
                .startAt(delayDate)
                .build();

        Date resD = scheduler.rescheduleJob(oldTriggerKey, newTrigger); //重置
//        LOGGER.info("重置了{}竞标单,增加了{} /m延时", bidding.getName(), Float.valueOf(addDelay/1000/60));

        Map<String, Object> params = new HashMap<>();
        params.put("delayTime", delayTime / 1000);
        params.put("groupId", newGroupId);
        return params;
    }

    @Value("${group-name}")
    public void setGroupName(String groupName) {
        BiddingTaskUtil.groupName = groupName;
    }
}
