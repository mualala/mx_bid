package com.meixin.bid.service.impl;

import com.meixin.bid.entity.BidDetails;
import com.meixin.bid.entity.Bidding;
import com.meixin.bid.mappers.dao.BidDetailsDao;
import com.meixin.bid.mappers.dao.BiddingDao;
import com.meixin.bid.service.BidDetailsService;
import com.meixin.bid.web.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:45 2018/6/5 0005
 */
@Service
public class BidDetailsServiceImpl implements BidDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(BidDetailsServiceImpl.class);

    @Autowired
    private BidDetailsDao bidDetailsDao;

    @Autowired
    private BiddingDao biddingDao;

    private String order(int mark) {
        String order = "asc";
        if (mark == 1) //招标
            order = "desc";
        return order;
    }

    private String order2(int mark) {
        String order = "<";
        if (mark == 2) //竞标
            order = ">";
        return order;
    }

    //出价的错误类型
    private enum Price {
        REFRESH(1001, "不能出价! 请参考最高报价(已刷新)"),
        NOT(1002, "不能出价! 竞标单名称不存在"),
        OVER(1003, "不能出价! 竞标单已经结束，请关闭当前窗口并[刷新主页]");

        private int code;
        private String msg;
        Price(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
    }

    @Override
    public SimpleResponse price(BidDetails bidDetails) {
        SimpleResponse result = null;

        Bidding bidding = biddingDao.queryByNameAndPid(bidDetails.getBidName(), bidDetails.getProductId());

        //判断竞标单是否结束
        if (bidding != null) {
            if (bidding.getStatus() == 2) { //竞标单已经结束
                result = new SimpleResponse(Price.OVER.getCode(), Price.OVER.getMsg());
                return result;
            }
        }else {
            result = new SimpleResponse(Price.NOT.getCode(), Price.NOT.getMsg());
            return result;
        }

        int mark = bidDetails.getMark();
        int count = 0;
        float price = bidDetails.getPrice();
        Float limitPrice = bidDetailsDao.limitPrice(bidDetails.getBidName(), bidDetails.getProductId(), bidDetails.getUid(), order(mark));
        switch (mark) {
            case 1: //招标
                if (limitPrice == null) { //第一次出价
                    float min = bidding.getStartPrice() + bidding.getStep();
                    if (price >= min) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }else { //非第一次出价
                    if(bidDetails.getPrice() >= (limitPrice + bidding.getStep())) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }
                break;
            case 2:
                if (limitPrice == null) { //第一次出价
                    float max = bidding.getStartPrice() - bidding.getStep();
                    if (price <= max) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }else {
                    if((limitPrice - bidDetails.getPrice()) >= bidding.getStep()) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }
                break;
            default: LOGGER.error("竞标单mark标记异常...");
        }

        if (count == 1) {
            BidDetails optimalPrice = lastPrice(bidDetails.getBidName(), bidDetails.getProductId(), bidDetails.getMark(), bidDetails.getUid());
            result = SimpleResponse.OK(optimalPrice);
        }else {
            result = new SimpleResponse(Price.REFRESH.getCode(), Price.REFRESH.getMsg());
        }
        return result;
    }

    /**
     * @Desc:   刷新最优报价(参考价)
     * @Author: yanghm
     * @Param:
     * @Date:   13:50 2018/6/8 0008
     * @Return:
     */
    @Override
    public BidDetails lastPrice(String bidName, int productId, int mark, int uid) {
        String order = order(mark);
        String order2 = order2(mark);
        BidDetails bidDetails = bidDetailsDao.queryOptimalBidDetail(bidName, productId, order2, order, uid);
        //最高 或 最低报价
        Float limitPrice = bidDetailsDao.limitPrice(bidName, productId, uid, order);
        if (limitPrice != null) {
            if (bidDetails == null) {
                bidDetails = new BidDetails();
//                bidDetails.setRank(1);
            }
            bidDetails.setLimitPrice(limitPrice);
        }
        return bidDetails;
    }


}
