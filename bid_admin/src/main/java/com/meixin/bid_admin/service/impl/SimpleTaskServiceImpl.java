package com.meixin.bid_admin.service.impl;

import com.meixin.bid_admin.mappers.dao.BiddingDao;
import com.meixin.bid_admin.service.SimpleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 08:34 2018/5/29 0029
 */
@Service
public class SimpleTaskServiceImpl implements SimpleTaskService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(SimpleTaskServiceImpl.class);

    @Autowired
    private BiddingDao biddingDao;

    @Override
    public int addSimpleJob(String bidName, int uid, int status) {

        int count = biddingDao.setBidStatus(bidName, uid, status);

        LOGGER.info("sertvie time={}, bidName={}, uid={}, 设置了 [{}] 条竞标单记录",System.currentTimeMillis(),  bidName, uid, count);

        return count;
    }


}
