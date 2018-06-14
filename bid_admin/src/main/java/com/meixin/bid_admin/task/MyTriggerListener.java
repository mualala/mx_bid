package com.meixin.bid_admin.task;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:18 2018/5/28 0028
 */
public class MyTriggerListener implements TriggerListener {
    String triggerName;

    public MyTriggerListener(String triggerName) {
        this.triggerName = triggerName;
    }

    @Override
    public String getName() {
        System.err.println(this.triggerName);
        return this.triggerName;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.err.println("trigger triggerFired/.......");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        System.err.println("trigger vetoJobExecution/....... false");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        System.err.println("trigger triggerMisfired/....... false");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        //triger每次执行完成时触发

        System.err.println("trigger triggerComplete/....... false, "+ System.currentTimeMillis());
    }

}
