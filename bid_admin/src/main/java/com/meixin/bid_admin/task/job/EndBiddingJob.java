package com.meixin.bid_admin.task.job;

import com.meixin.bid_admin.service.BiddingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Desc：   竞标单 结束
 * @Author： yanghm
 * @Date： 15:40 2018/5/28 0028
 */
public class EndBiddingJob extends BaseJob {

    private final Logger LOGGER = LoggerFactory.getLogger(EndBiddingJob.class);

    @Autowired
    private BiddingService biddingService;

    @Override
    int doService(String name, int uid, String taskName, String groupId) {
        int count = biddingService.setStatus(name, uid, 2);
        return count;
    }


}
