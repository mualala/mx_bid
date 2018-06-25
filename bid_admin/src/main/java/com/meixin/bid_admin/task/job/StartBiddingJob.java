package com.meixin.bid_admin.task.job;

import com.meixin.bid_admin.service.BiddingService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @Desc：  竞标单 开启
 * @Author： yanghm
 * @Date： 15:40 2018/5/28 0028
 */
public class StartBiddingJob extends BaseJob {

    private final Logger LOGGER = LoggerFactory.getLogger(StartBiddingJob.class);

    @Autowired
    private BiddingService biddingService;

    @Override
    int doService(String name, int uid, String taskName, String groupId) {
//        int count = biddingService.startBidding(name, uid, 1, taskName, groupId);
        int count = biddingService.setStatus(name, uid, 1);
        return count;
    }

}
