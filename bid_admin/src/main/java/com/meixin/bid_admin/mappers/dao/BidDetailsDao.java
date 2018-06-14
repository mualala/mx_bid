package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.BidDetails;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:55 2018/6/6 0006
 */
public interface BidDetailsDao extends IMapper<BidDetails> {

    List<BidDetails> queryBidDetailsListByUid(BasePageCondition condition);

    int queryBidDetailsListTotal(BasePageCondition condition);

    /**
     * @Desc:   最高供应商报价列表
     * @Author: yanghm
     * @Param:
     * @Date:   10:32 2018/6/8 0008
     * @Return:
     */
    List<BidDetails> selectMax(@Param("bidName") String bidName);

    List<BidDetails> selectMin(@Param("bidName") String bidName);

    /**
     * @Desc:   每个供应商的最高出价
     * @Author: yanghm
     * @Param:
     * @Date:   17:02 2018/6/8 0008
     * @Return:
     */
    List<BidDetails> allSupplierPriceMax(@Param("bidName") String bidName, @Param("productId") int productId);

    List<BidDetails> allSupplierPriceMin(@Param("bidName") String bidName, @Param("productId") int productId);

    List<BidDetails> queryPriceDetails(BasePageCondition condition);

    List<BidDetails> queryPriceDetailsTotal(BasePageCondition condition);

}
