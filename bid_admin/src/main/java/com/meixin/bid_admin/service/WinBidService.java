package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.WinBid;
import com.meixin.bid_admin.web.dto.BasePageCondition;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:23 2018/6/11 0011
 */
public interface WinBidService {

    /**
     * 后台中标操作
     * @param winBid
     * @return
     */
    int winBid(WinBid winBid);

    JSONObject doBidDetailsReport(BasePageCondition condition);

}
