package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:29 2018/5/14 0014
 */
public interface BiddingDao extends IMapper<Bidding> {

    @Select("select * from bidding where name = #{name}")
    List<Bidding> queryByName(@Param("name") String name);

    @Select("select * from bidding where name = #{name} and status=1")
    List<Bidding> queryByNameAndStatus(@Param("name") String name);

    @Update("update bidding set finish=1 where name=#{name}")
    int finish(@Param("name") String bidName);

    @Select("select * from bidding where name = #{name} and product_id=${productId}")
    Bidding queryByNameAndPid(@Param("name") String name, @Param("productId") int productId);

    List<Bidding> queryBiddingListByUid(BasePageCondition condition);

    List<Bidding> queryCheckBiddingListByUid(BasePageCondition condition);

    int queryBiddingListTotal(BasePageCondition condition);

    int queryCheckBiddingListTotal(BasePageCondition condition);

    int deleteBiddings(Map<String, Object> params);

    List<Bidding> queryProductsByBiddingName(@Param("name") String name, @Param("uid") int uid);

    int releaseBiddings(Map<String, Object> params);

    /**
     * @Desc:   开启竞标单是设置状态  和 schedul的triggerkey
     * @Author: yanghm
     * @Param:
     * @Date:   14:07 2018/6/22 0022
     * @Return:
     */
    @Update("update bidding set task_name=#{taskName}, group_id=#{groupId} where uid=${uid} and name=#{bidName}")
    int setTaskInfo(@Param("bidName") String bidName, @Param("uid") int uid,
                     @Param("taskName") String taskName, @Param("groupId") String groupId);

    /**
     * @Desc:   更新竞标单延迟后的结束时间
     * @Author: yanghm
     * @Param:
     * @Date:   13:35 2018/6/25 0025
     * @Return:
     */
    @Update("update bidding set end_time=FROM_UNIXTIME(${delayTime}), group_id=#{groupId} where uid=${uid} and name=#{bidName}")
    int updateDelayTime(Map<String, Object> params);

    /**
     * @Desc:   设置竞标单结束的状态
     * @Author: yanghm
     * @Param:  status 标单状态 0：发布状态     1：正在竞标中     2：结束
     * @Date:   09:19 2018/5/29 0029
     * @Return:
     */
    @Update("update bidding set status=${status} where uid=${uid} and name=#{bidName}")
    int setBidStatus(@Param("bidName") String bidName, @Param("uid") int uid, @Param("status") int status);


    List<Bidding> queryBiddingListByBidNames(Map<String, Object> params);

    int checkokBiddings(Map<String, Object> params);

    /**
     * @Desc:   存成草稿竞标单
     * @Author: yanghm
     * @Param:
     * @Date:   11:09 2018/6/15 0015
     * @Return:
     */
    int saveToDraft(Map<String, Object> params);


}
