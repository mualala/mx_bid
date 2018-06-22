package com.meixin.bid_admin.service.impl;

import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.mappers.dao.BiddingDao;
import com.meixin.bid_admin.service.RescheduleService;
import com.meixin.bid_admin.task.BiddingTaskUtil;
import com.meixin.bid_admin.web.support.Utils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 16:34 2018/6/22 0022
 */
@Service
public class RescheduleServiceImpl implements RescheduleService {
    private final Logger LOGGER = LoggerFactory.getLogger(RescheduleServiceImpl.class);

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @Autowired
    private BiddingDao biddingDao;

    @Override
    public ResponseEntity reschedule(String bidName) {
        ResponseEntity result = null;
        try {
            List<Bidding> biddingList = biddingDao.queryByNameAndStatus(bidName);
            if (!biddingList.isEmpty()) {
                Bidding bidding = biddingList.get(0);
                BiddingTaskUtil.rescheduleBidding(scheduler, bidding); //重置
                result = ResponseEntity.ok("延时成功");
            }else {
                result = new ResponseEntity("竞标单已经结束", HttpStatus.NOT_IMPLEMENTED);
            }
        } catch (SchedulerException e) {
            LOGGER.error("重置scheduler异常{},竞标单定时器错误,竞标单名称=[{}]", e, bidName);
            result = new ResponseEntity("重置定时器失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
