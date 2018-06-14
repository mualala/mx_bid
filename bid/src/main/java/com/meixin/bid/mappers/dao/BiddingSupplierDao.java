package com.meixin.bid.mappers.dao;

import com.meixin.bid.entity.BiddingSupplier;
import com.meixin.bid.mappers.common.IMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:26 2018/6/12 0012
 */
public interface BiddingSupplierDao extends IMapper<BiddingSupplier> {

//    @Select("select count(bid_name) from bidding_supplier WHERE bid_name=#{bidName} AND suid=${suid}")
//    int countBySuidAndBidName(@Param("bidName") String name, @Param("suid") int suid);

}
