package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.BiddingSupplier;
import com.meixin.bid_admin.mappers.common.IMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:26 2018/6/12 0012
 */
public interface BiddingSupplierDao extends IMapper<BiddingSupplier> {


    List<BiddingSupplier> querySuppliersByBiddingName(@Param("bidName") String name);

    int deleteSus(Map<String, Object> params);

}
