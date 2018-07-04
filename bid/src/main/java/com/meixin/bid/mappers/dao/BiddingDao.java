package com.meixin.bid.mappers.dao;

import com.meixin.bid.entity.Bidding;
import com.meixin.bid.mappers.common.IMapper;
import com.meixin.bid.web.dto.BasePageCondition;
import com.meixin.bid.web.dto.BiddingCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:29 2018/5/14 0014
 */
public interface BiddingDao extends IMapper<Bidding> {

    @Select("select * from bidding where name = #{name} limit 0, 1")
    Bidding queryByName(@Param("name") String name);

    @Select("select * from bidding where name = #{name} and product_id=${productId} limit 0, 1")
    Bidding queryByNameAndPid(@Param("name") String name, @Param("productId") int productId);

    List<Bidding> queryBiddingListByUid(BasePageCondition condition);

    int queryBiddingListTotal(BasePageCondition condition);

    List<Bidding> queryProductsByBiddingName(@Param("name") String name, @Param("uid") int uid);


    List<Bidding> queryMyBiddingListByUid(BiddingCondition biddingCondition);

    int queryMyBiddingListTotal(BiddingCondition biddingCondition);

    /**
     * @Desc:   供应商参与的竞标单每个产品出价情况
     * @Author: yanghm
     * @Param:
     * @Date:   16:02 2018/6/20 0020
     * @Return:
     */
    List<Bidding> queryMyBiddingMaxPriceDetails(BiddingCondition biddingCondition);
    List<Bidding> queryMyBiddingMinPriceDetails(BiddingCondition biddingCondition);

}
