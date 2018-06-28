package com.meixin.bid.service.impl;

import com.meixin.bid.entity.BidDetails;
import com.meixin.bid.entity.Bidding;
import com.meixin.bid.mappers.dao.BidDetailsDao;
import com.meixin.bid.mappers.dao.BiddingDao;
import com.meixin.bid.service.BidDetailsService;
import com.meixin.bid.web.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:45 2018/6/5 0005
 */
@Service
public class BidDetailsServiceImpl implements BidDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(BidDetailsServiceImpl.class);

    @Value("${delay-url}")
    private String delayUrl;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BidDetailsDao bidDetailsDao;

    @Autowired
    private BiddingDao biddingDao;

    //出价的错误类型
    private enum Price {
        REFRESH(1001, "不能出价,请参考最高报价(已刷新)"),
        NOT(1002, "不能出价,竞标单名称不存在"),
        OVER(1003, "不能出价,竞标单已经结束，请关闭当前窗口并[刷新主页]"),
        DUPLICATE_PRICE(1004, "不能出价,已有相同出价");

        private int code;
        private String msg;
        Price(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
    }

    @Override
    public SimpleResponse price(BidDetails bidDetails) {
        SimpleResponse result = null;

        Bidding bidding = biddingDao.queryByNameAndPid(bidDetails.getBidName(), bidDetails.getProductId());

        //判断竞标单是否结束
        if (bidding != null) {
            if (bidding.getStatus() == 2) { //竞标单已经结束
                result = new SimpleResponse(Price.OVER.getCode(), Price.OVER.getMsg());
                return result;
            }
        }else {
            result = new SimpleResponse(Price.NOT.getCode(), Price.NOT.getMsg());
            return result;
        }

        //是否出了重复价
        BidDetails bd = bidDetailsDao.queryDuplicatPrice(bidDetails.getBidName(), bidDetails.getProductId(), bidDetails.getPrice());
        if (bd != null) {
            result = new SimpleResponse(Price.DUPLICATE_PRICE.getCode(), Price.DUPLICATE_PRICE.getMsg());
            return result;
        }

        int mark = bidDetails.getMark();
        int count = 0;
        float price = bidDetails.getPrice();
        Float limitPrice = bidDetailsDao.limitPrice(bidDetails.getBidName(), bidDetails.getProductId(), bidDetails.getUid(), order2(mark));
        switch (mark) {
            case 1: //招标
                if (limitPrice == null) { //第一次出价
                    float min = bidding.getStartPrice() + bidding.getStep();
                    if (price >= min) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }else { //非第一次出价
                    if(bidDetails.getPrice() >= (limitPrice + bidding.getStep())) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }
                break;
            case 2:
                if (limitPrice == null) { //第一次出价
                    float max = bidding.getStartPrice() - bidding.getStep();
                    if (price <= max) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }else {
                    if((limitPrice - bidDetails.getPrice()) >= bidding.getStep()) {
                        count = bidDetailsDao.insertSelective(bidDetails);
                    }
                }
                break;
            default: LOGGER.error("竞标单mark标记异常...");
        }

        if (count == 1) {
            BidDetails optimalPrice = lastPrice(bidDetails.getBidName(), bidDetails.getProductId(), bidDetails.getMark(), bidDetails.getUid());
            result = SimpleResponse.OK(optimalPrice);

            // 向admin发送重置定时器消息（只有admin才能检查是否要重置,因为延时是在admin的jvm内存中）
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<Object> httpEntity = new HttpEntity<>(bidDetails.getBidName(), headers);

            ListenableFuture<ResponseEntity> re = (ListenableFuture<ResponseEntity>) asyncRestTemplate.put(delayUrl + "/" + bidDetails.getBidName(), httpEntity);

//            asyncRestTemplate.exchange(delayUrl, HttpMethod.POST, httpHeaders, ResponseEntity.class);
//            ListenableFuture<ResponseEntity<ResponseEntity>> res = asyncRestTemplate.postForEntity(delayUrl, httpEntity, ResponseEntity.class, bidDetails.getBidName());
            re.addCallback(new ListenableFutureCallback<ResponseEntity>() {
                //调用成功
                @Override
                public void onSuccess(ResponseEntity resp) {}

                //调用失败
                @Override
                public void onFailure(Throwable t) {
                    LOGGER.error("竞标单延时请求失败,原因:{}", t.getMessage());
                }
            });
        }else {
            result = new SimpleResponse(Price.REFRESH.getCode(), Price.REFRESH.getMsg());
        }
        return result;
    }

    /**
     * @Desc:   刷新最优报价(参考价)
     * @Author: yanghm
     * @Param:
     * @Date:   13:50 2018/6/8 0008
     * @Return:
     */
    @Override
    public BidDetails lastPrice(String bidName, int productId, int mark, int uid) {
        String order = order(mark);
        BidDetails bidDetails = null;
        switch (mark) {
            case 1://招标
                bidDetails = bidDetailsDao.queryMaxOptimalBidDetail(bidName, productId, order, uid);
                break;
            case 2:
                bidDetails = bidDetailsDao.queryMinOptimalBidDetail(bidName, productId, order, uid);
                break;
            default: LOGGER.error("竞标单{}，产品{}，刷新最优出价失败，mark为{} 不存在", bidName, productId, mark);
        }

//        BidDetails bidDetails = bidDetailsDao.queryOptimalBidDetail(bidName, productId, order2, order, uid);
        //最高 或 最低报价
        Float limitPrice = bidDetailsDao.limitPrice(bidName, productId, uid, order2(mark));
        if (limitPrice != null) {
            if (bidDetails == null) {
                bidDetails = new BidDetails();
//                bidDetails.setRank(1);
            }
            bidDetails.setLimitPrice(limitPrice);
        }
        return bidDetails;
    }

    private String order2(int mark) {
        String order = "asc";
        if (mark == 1) //招标
            order = "desc";
        return order;
    }

    private String order(int mark) {
        String order = "<";//招标
        if (mark == 2) //竞标
            order = ">";
        return order;
    }


}
