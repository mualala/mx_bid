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
     * @Desc:   中标操作
     * @Author: yanghm
     * @Param:
     * @Date:   15:16 2018/6/15 0015
     * @Return:
     */
    int winBid(WinBid winBid);

    JSONObject doBidDetailsReport(BasePageCondition condition);

}
