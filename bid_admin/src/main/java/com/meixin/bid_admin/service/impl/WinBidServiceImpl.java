package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.WinBid;
import com.meixin.bid_admin.mappers.dao.BiddingDao;
import com.meixin.bid_admin.mappers.dao.WinBidDao;
import com.meixin.bid_admin.service.WinBidService;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:23 2018/6/11 0011
 */
@Service
public class WinBidServiceImpl implements WinBidService {
    private final Logger LOGGER = LoggerFactory.getLogger(WinBidServiceImpl.class);

    @Autowired
    private WinBidDao winBidDao;

    @Autowired
    private BiddingDao biddingDao;

    @Override
    public int winBid(WinBid winBid) {
        String bidName = winBid.getBidName();
        int productId = winBid.getProductId();
        int supplierId = winBid.getSuid();
        List<WinBid> winBids = winBidDao.queryWinBidByNameAndPid(bidName, productId, supplierId);//同一标单的同一产品可以多个人设置中标(只能是不同供应商中标)
        if (winBids != null && !winBids.isEmpty()) {
            return 0;//已经中标了
        }

        int count = winBidDao.insertSelective(winBid);
        //更新竞标单的所有产品是否已经全部中标完毕
        int bidedCount = winBidDao.countByBidName(bidName);
        int bidCount = biddingDao.queryByName(bidName).size();
        if (bidedCount == bidCount) {
            biddingDao.finish(bidName);
        }

        return count;
    }

    @Override
    public JSONObject doBidDetailsReport(BasePageCondition condition) {
        JSONObject result = new JSONObject();

        condition.pageSettings();
        result.put("rows", winBidDao.queryWinBidListByUid(condition));
        result.put("total", winBidDao.queryWinBidListTotal(condition));
        return result;
    }

}
