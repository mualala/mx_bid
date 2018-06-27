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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
                //判断是否增加延时
                long now = System.currentTimeMillis();
                long over = bidding.getEndTime().getTime();
                long diff = over - now;
                long delay = Utils.Delay.getDelay();
                if (diff > 0 && diff <= delay) {//如果剩余时间 <= 设定的延时时间，那么增加延时
                    long addDelay = delay - diff;//固定延时
                    Map<String, Object> params = BiddingTaskUtil.rescheduleBidding(scheduler, bidding, addDelay); //重置

                    //更新数据库延迟后的结束时间, mysql必须除以1000
//                    biddingDao.updateDelayTime(delayTime / 1000, bidName, bidding.getUid());
                    params.put("bidName", bidName);
                    params.put("uid", bidding.getUid());
                    biddingDao.updateDelayTime(params);

                    result = ResponseEntity.ok("延时成功");
                }
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
