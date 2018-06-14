package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.BidDetails;
import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.mappers.dao.BidDetailsDao;
import com.meixin.bid_admin.mappers.dao.BiddingDao;
import com.meixin.bid_admin.service.BidDetailsService;
import com.meixin.bid_admin.web.dto.BidDetailsCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:55 2018/6/6 0006
 */
@Service
public class BidDetailsServiceImpl implements BidDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(BidDetailsServiceImpl.class);

    @Autowired
    private BidDetailsDao bidDetailsDao;

    @Autowired
    private BiddingDao biddingDao;

    @Override
    public JSONObject bidDetailsReport(BidDetailsCondition condition) {
        JSONObject result = new JSONObject();

        condition.pageSettings();
        List<BidDetails> supplierList = bidDetailsDao.queryBidDetailsListByUid(condition);
        result.put("rows", supplierList);
        result.put("total", bidDetailsDao.queryBidDetailsListTotal(condition));
        return result;
    }

    @Override
    public List<BidDetails> optimalPrice(String bidName) {
        List<BidDetails> bidDetailsList = null;

        List<Bidding> biddings = biddingDao.queryByName(bidName);
        if(biddings != null || !biddings.isEmpty()) {
            Bidding bidding = biddings.get(0);
            int mark = bidding.getMark();

            switch (mark) {
                case 1: bidDetailsList = bidDetailsDao.selectMax(bidName); break;
                case 2: bidDetailsList = bidDetailsDao.selectMin(bidName); break;
                default: LOGGER.error("选标时 mark类型为 {} 错误(只能为1和2)", mark); break;
            }
        }
        return bidDetailsList;
    }

    @Override
    public List<BidDetails> allSupplierOptimalPriceDetail(String bidName, int productId) {
        List<BidDetails> bidDetailsList = null;

        Bidding bidding = biddingDao.queryByNameAndPid(bidName, productId);
        if(bidding != null) {
            int mark = bidding.getMark();
            switch (mark) {
                case 1: bidDetailsList = bidDetailsDao.allSupplierPriceMax(bidName, productId); break;
                case 2: bidDetailsList = bidDetailsDao.allSupplierPriceMin(bidName, productId); break;
                default: LOGGER.error("选标时 mark类型为 {} 错误(只能为1和2)", mark); break;
            }
        }

        return bidDetailsList;
    }

    @Override
    public JSONObject priceDetails(BidDetailsCondition condition) {
        JSONObject result = new JSONObject();

        condition.pageSettings();
        List<BidDetails> supplierList = bidDetailsDao.queryPriceDetails(condition);
        result.put("rows", supplierList);
        result.put("total", bidDetailsDao.queryPriceDetailsTotal(condition));
        return result;
    }

}
