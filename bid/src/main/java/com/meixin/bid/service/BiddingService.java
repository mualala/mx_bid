package com.meixin.bid.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.entity.Bidding;
import com.meixin.bid.web.dto.BiddingCondition;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 15:33 2018/5/31 0031
 */
public interface BiddingService {

    JSONObject biddingReport(BiddingCondition biddingCondition);

    List<Bidding> getProductsByBiddingName(String name, int uid);

}
