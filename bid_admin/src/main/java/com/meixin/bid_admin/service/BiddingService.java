package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.entity.BiddingSupplier;
import com.meixin.bid_admin.web.dto.BiddingCondition;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:59 2018/5/24 0024
 */
public interface BiddingService {

    List<Bidding> getBiddingName(String name);

    int createBidding(List<Bidding> biddings);

    JSONObject biddingReport(BiddingCondition biddingCondition);

    JSONObject checkBiddingReport(BiddingCondition biddingCondition);

    int deleteBiddings(String names, int uid);

    List<Bidding> getProductsByBiddingName(String name, int uid);

    List<BiddingSupplier> getSuppliersByBiddingName(String name);

    int modifyBidding(List<Bidding> biddings);

    int biddingRelease(String names, int uid);

    int setStatus(String bidName, int uid, int status);

    int checkBidding(List<String> bidNames, int status);

}
