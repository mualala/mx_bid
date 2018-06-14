package com.meixin.bid_admin.service;

import org.quartz.SchedulerException;

import java.text.ParseException;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 08:30 2018/5/29 0029
 */
public interface SimpleTaskService {

    /* key：竞标单名称
     * jobKey: uid
     *
     */

    int addSimpleJob(String bidName, int uid, int status);

}
