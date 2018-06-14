package com.meixin.bid.mappers.dao;

import com.meixin.bid.entity.BidDetails;
import com.meixin.bid.mappers.common.IMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:47 2018/6/5 0005
 */
public interface BidDetailsDao extends IMapper<BidDetails> {

//    @Select("SELECT * FROM bid_details WHERE uid=#{uid} AND bid_name=#{bidName} AND product_id=#{productId} ORDER BY price DESC LIMIT 0, 1")
    /**
     * @Desc:   查询供应商的最高或最低报价 得到排名
     * @Author: yanghm
     * @Param:
     * @Date:   17:13 2018/6/5 0005
     * @Return:
     */
    BidDetails queryOptimalBidDetail(@Param("bidName") String bidName, @Param("productId") int productId,
                                     @Param("order") String order2, @Param("order3") String order3, @Param("uid") int uid);

    @Select("SELECT price FROM bid_details WHERE bid_name=#{bidName} AND product_id=#{productId} AND uid=${uid} ORDER BY price ${order} LIMIT 0, 1")
    Float limitPrice(@Param("bidName") String bidName, @Param("productId") int productId, @Param("uid") int uid, @Param("order") String order);

}
