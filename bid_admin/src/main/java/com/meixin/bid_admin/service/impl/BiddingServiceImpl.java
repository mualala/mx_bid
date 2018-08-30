package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.entity.BiddingSupplier;
import com.meixin.bid_admin.mappers.dao.BiddingDao;
import com.meixin.bid_admin.mappers.dao.BiddingSupplierDao;
import com.meixin.bid_admin.service.BiddingService;
import com.meixin.bid_admin.web.dto.BiddingCondition;
import com.meixin.bid_admin.task.BiddingTaskUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:59 2018/5/24 0024
 */
@Service
public class BiddingServiceImpl implements BiddingService {

    private final Logger LOGGER = LoggerFactory.getLogger(BiddingServiceImpl.class);

    @Autowired
    private BiddingDao biddingDao;

    @Autowired
    private BiddingSupplierDao biddingSupplierDao;

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @Override
    public List<Bidding> getBiddingName(String name) {
        return biddingDao.queryByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int createBidding(List<Bidding> biddings) {
        //待审核竞标单
        int count = biddingDao.insertList(biddings);
        Bidding bidding = biddings.get(0);
        //添加约束的供应商列表
        String supplierIds = bidding.getRecSuppliers();
        if (supplierIds != null) {
            List<BiddingSupplier> bsList = new ArrayList<>();
            String[] suIds = StringUtils.split(supplierIds, "-");
            for (int i = 0; i < suIds.length; i++) {
                BiddingSupplier bs = new BiddingSupplier();
                bs.setBidName(bidding.getName());
                bs.setSuid(Integer.valueOf(suIds[i]));
                bsList.add(bs);
            }
            biddingSupplierDao.insertList(bsList);
        }
        /*
        if (count > 0 && bidding.getType() == 0) {//不是草稿
            String uid = String.valueOf(bidding.getUid());
            BiddingTaskUtil.startBiddingTask(scheduler, bidding.getName(), uid, bidding.getRecStartTime());
            BiddingTaskUtil.endBiddingTask(scheduler, bidding.getName(), uid, bidding.getRecEndTime());
        }
        */
        return count;
    }

    @Override
    public JSONObject biddingReport(BiddingCondition biddingCondition) {
        JSONObject result = new JSONObject();

        biddingCondition.pageSettings();
        List<Bidding> productList = biddingDao.queryBiddingListByUid(biddingCondition);
        result.put("rows", productList);
        result.put("total", biddingDao.queryBiddingListTotal(biddingCondition));
        return result;
    }

    @Override
    public JSONObject checkBiddingReport(BiddingCondition biddingCondition) {
        JSONObject result = new JSONObject();

        biddingCondition.pageSettings();
        List<Bidding> productList = biddingDao.queryCheckBiddingListByUid(biddingCondition);
        result.put("rows", productList);
        result.put("total", biddingDao.queryCheckBiddingListTotal(biddingCondition));
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteBiddings(String names, int uid) {
        String[] _names = StringUtils.split(names, "-");
        List<String> nameList = new ArrayList<>();
        for(String name : _names) {
            nameList.add(name);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("names", nameList);
        params.put("uid", uid);

        biddingSupplierDao.deleteSus(params);//删除 约束的供应商
        int count = biddingDao.deleteBiddings(params);
        return count;
    }

    @Override
    public List<Bidding> getProductsByBiddingName(String name, int uid) {
        List<Bidding> biddings = biddingDao.queryProductsByBiddingName(name, uid);
        return biddings;
    }

    @Override
    public List<BiddingSupplier> getSuppliersByBiddingName(String name) {
        List<BiddingSupplier> biddingSupplierList = biddingSupplierDao.querySuppliersByBiddingName(name);
        return biddingSupplierList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyBidding(List<Bidding> biddings) {
        int modifyCount = 0;
        // 先删除原始数据
        if(biddings != null && !biddings.isEmpty()) {
            String name = biddings.get(0).getOriginalName();
            int uid = biddings.get(0).getUid();
            int count = deleteBiddings(name, uid);

            // 成功删除时
            if (count > 0) {
                modifyCount = createBidding(biddings);
            }
            LOGGER.error("修改竞标单数据时 删除count={}, 插入count={}", count, modifyCount);
        }
        return modifyCount;
    }

    @Override
    public int biddingRelease(String names, int uid) {
        String[] _names = StringUtils.split(names, "-");
        List<String> nameList = new ArrayList<>();
        for(String name : _names) {
            nameList.add(name);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("names", nameList);
        params.put("uid", uid);
        int count = biddingDao.releaseBiddings(params);
        return count;
    }

    @Override
    public int setStatus(String bidName, int uid, int status) {
        int count = biddingDao.setBidStatus(bidName, uid, status);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int checkBidding(List<String> bidNames, int status) {
        int count = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("names", bidNames);

        if (status == 0) {//审核通过
            count = biddingDao.checkokBiddings(map);
            List<Bidding> biddingList = biddingDao.queryBiddingListByBidNames(map);
            for (Bidding bidding : biddingList) {
                if (count > 0 && bidding.getType() == 0) {//不是草稿
                    String uid = String.valueOf(bidding.getUid());
                    BiddingTaskUtil.startBiddingTask(scheduler, bidding.getName(), uid, bidding.getStartTime().toString(), biddingDao);
                    BiddingTaskUtil.endBiddingTask(scheduler, bidding.getName(), uid, bidding.getEndTime().toString(), biddingDao);
                }
            }
        }else {//保存草稿
            count = biddingDao.saveToDraft(map);
        }

        return count;
    }


}
