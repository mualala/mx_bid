package com.meixin.bid.service;

import com.meixin.bid.entity.BidDetails;
import com.meixin.bid.web.support.SimpleResponse;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:45 2018/6/5 0005
 */
public interface BidDetailsService {

    SimpleResponse price(BidDetails bidDetails);

    BidDetails lastPrice(String bidName, int productId, int mark, int uid);

}
