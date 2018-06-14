package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.BidDetails;
import com.meixin.bid_admin.entity.WinBid;
import com.meixin.bid_admin.web.dto.BidDetailsCondition;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:53 2018/6/6 0006
 */
public interface BidDetailsService {

    JSONObject bidDetailsReport(BidDetailsCondition condition);

    List<BidDetails> optimalPrice(String bidName);

    List<BidDetails> allSupplierOptimalPriceDetail(String bidName, int productId);

    JSONObject priceDetails(BidDetailsCondition condition);


}
