package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.WinBid;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:23 2018/6/11 0011
 */
public interface WinBidDao extends IMapper<WinBid> {

    @Select("select count(0) from win_bid where bid_name=#{bidName} limit 0, 1")
    int countByBidName(@Param("bidName") String bidName);

    @Select("select * from win_bid where bid_name=#{bidName} and product_id=${productId} and suid=${suid}")
    List<WinBid> queryWinBidByNameAndPid(@Param("bidName") String bidName, @Param("productId") int productId, @Param("suid") int suid);

    List<WinBid> queryWinBidListByUid(BasePageCondition condition);

    int queryWinBidListTotal(BasePageCondition condition);


}
