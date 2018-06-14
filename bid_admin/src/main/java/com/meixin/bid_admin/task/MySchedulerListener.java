package com.meixin.bid_admin.task;

import com.meixin.bid_admin.mappers.dao.BiddingDao;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 16:36 2018/5/28 0028
 */
@Component
public class MySchedulerListener implements SchedulerListener {

//    @Autowired
//    private BiddingDao biddingDao;

    @Override
    public void jobScheduled(Trigger trigger) {
        System.err.println("jobScheduled");
    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.err.println("Schedule jobUnscheduled");
    }

    @Override
    public void triggerFinalized(Trigger trigger) {
        //triger最终执行完成时 触发
//        JobKey jobKey = trigger.getJobKey();
//        String bidName = jobKey.getName();
//        int uid = Integer.parseInt(jobKey.getGroup());
//
//        int count = biddingDao.setBidStatus(bidName, uid, 2);
//
        System.err.println("Schedule triggerFinalized,竞标单结束更新了 +" );
    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.err.println("Schedule triggerPaused");
    }

    @Override
    public void triggersPaused(String s) {
        System.err.println("Schedule triggersPaused");
    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.err.println("Schedule triggerResumed");
    }

    @Override
    public void triggersResumed(String s) {
        System.err.println("Schedule triggersResumed");
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.err.println("Schedule jobAdded");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.err.println("Schedule jobDeleted");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.err.println("Schedule jobPaused");
    }

    @Override
    public void jobsPaused(String s) {
        System.err.println("Schedule jobsPaused");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.err.println("Schedule jobResumed");
    }

    @Override
    public void jobsResumed(String s) {
        System.err.println("Schedule jobsResumed");
    }

    @Override
    public void schedulerError(String s, SchedulerException e) {
        System.err.println("Schedule schedulerError");
    }

    @Override
    public void schedulerInStandbyMode() {
        System.err.println("Schedule schedulerInStandbyMode");
    }

    @Override
    public void schedulerStarted() {
        System.err.println("Schedule schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
        System.err.println("Schedule schedulerStarting");
    }

    @Override
    public void schedulerShutdown() {
        System.err.println("Schedule   schedul 【shut】 down ...");
    }

    @Override
    public void schedulerShuttingdown() {
        System.err.println("Schedule   schedul 【shutting】 down ...");
    }

    @Override
    public void schedulingDataCleared() {
        System.err.println("Schedule   schedulingDataCleared");
    }

}
