package com.meixin.bid.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.entity.Bidding;
import com.meixin.bid.mappers.dao.BiddingDao;
import com.meixin.bid.mappers.dao.BiddingSupplierDao;
import com.meixin.bid.service.BiddingService;
import com.meixin.bid.web.dto.BiddingCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 15:33 2018/5/31 0031
 */
@Service
public class BiddingServiceImpl implements BiddingService {
    private final Logger LOGGER = LoggerFactory.getLogger(BiddingServiceImpl.class);

    @Autowired
    private BiddingDao biddingDao;

    @Autowired
    private BiddingSupplierDao biddingSupplierDao;

    @Override
    public JSONObject biddingReport(BiddingCondition biddingCondition) {
        JSONObject result = new JSONObject();

//        String bidName = biddingCondition.getName();
//        int count = biddingSupplierDao.countBySuidAndBidName(bidName, biddingCondition.getUid());
//        if (count > 0) biddingCondition.setAll(1); //供应商有约束的(不允许看和操作的)竞标单

        biddingCondition.pageSettings();
        result.put("rows", biddingDao.queryBiddingListByUid(biddingCondition));
        result.put("total", biddingDao.queryBiddingListTotal(biddingCondition));
        return result;
    }

    @Override
    public List<Bidding> getProductsByBiddingName(String name, int uid) {
        List<Bidding> biddings = biddingDao.queryProductsByBiddingName(name, uid);
        return biddings;
    }

    @Override
    public JSONObject myBiddingReport(BiddingCondition biddingCondition) {
        JSONObject result = new JSONObject();
        biddingCondition.pageSettings();
        result.put("rows", biddingDao.queryMyBiddingListByUid(biddingCondition));
        result.put("total", biddingDao.queryMyBiddingListTotal(biddingCondition));
        return result;
    }

    @Override
    public List<Bidding> myBiddingDetails(BiddingCondition biddingCondition) {
        List<Bidding> myBiddingPriceDetails = null;
        int mark = biddingCondition.getMark();
        switch (mark) {
            case 1: myBiddingPriceDetails = biddingDao.queryMyBiddingMaxPriceDetails(biddingCondition); break;
            case 2: myBiddingPriceDetails = biddingDao.queryMyBiddingMinPriceDetails(biddingCondition); break;
            default: LOGGER.error("供应商{} 查询自己参与的竞标产品时，竞标单标志mark={} 错误", biddingCondition.getSuid(), biddingCondition.getMark());
        }
        return myBiddingPriceDetails;
    }


}
