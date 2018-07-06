package com.meixin.bid.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.entity.Bidding;
import com.meixin.bid.service.BiddingService;
import com.meixin.bid.web.dto.BiddingCondition;
import com.meixin.bid.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 15:31 2018/5/31 0031
 */
@RestController
@RequestMapping("/bidding")
public class BiddingController {
    private final Logger LOGGER = LoggerFactory.getLogger(BiddingController.class);

    @Autowired
    private BiddingService biddingService;

    @PostMapping("/biddingReport")
    public JSONObject biddingReport(BiddingCondition biddingCondition, HttpSession session) {
        biddingCondition.setUid(Utils.uidFromSession(session));
        biddingCondition.setSuid(Utils.suidFromSession(session));
        JSONObject result = biddingService.biddingReport(biddingCondition);
        return result;
    }

    @GetMapping("/{name}/bidding")
    public ResponseEntity productsByBiddingName(@PathVariable(name = "name") String name, HttpSession session) {
        List<Bidding> biddings = biddingService.getProductsByBiddingName(name, Utils.uidFromSession(session));
        return ResponseEntity.ok(biddings);
    }

    @PostMapping("/myBiddingReport")
    public JSONObject myBiddingReport(BiddingCondition biddingCondition, HttpSession session) {
        biddingCondition.setUid(Utils.uidFromSession(session));
        biddingCondition.setSuid(Utils.suidFromSession(session));
        JSONObject result = biddingService.myBiddingReport(biddingCondition);
        return result;
    }

    /**
     * @Desc:   我的项目 每个产品出价详情
     * @Author: yanghm
     * @Param:
     * @Date:   16:07 2018/6/20 0020
     * @Return:
     */
    @GetMapping("/{name}/{mark}/myBiddingDetail")
    public ResponseEntity myBiddingDetail(BiddingCondition biddingCondition, HttpSession session) {
        biddingCondition.setUid(Utils.uidFromSession(session));
        biddingCondition.setSuid(Utils.suidFromSession(session));
        List<Bidding> result = biddingService.myBiddingDetails(biddingCondition);
        JSONObject data = new JSONObject();
        data.put("rows", result);
        data.put("total", result.size());
        return ResponseEntity.ok(data);
    }

}
