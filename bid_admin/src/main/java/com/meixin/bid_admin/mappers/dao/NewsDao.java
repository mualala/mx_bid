package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.entity.News;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.BasePageCondition;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:49 2018/6/4 0004
 */
public interface NewsDao extends IMapper<News> {

    List<Product> queryNewsListByUid(BasePageCondition condition);

    int queryNewsListTotal(BasePageCondition condition);

    int deleteNews(Map<String, Object> params);



}
