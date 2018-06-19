package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.service.BiddingService;
import com.meixin.bid_admin.web.dto.BiddingCondition;
import com.meixin.bid_admin.web.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 14:49 2018/6/19 0019
 */
@RestController
@RequestMapping("/checkBidding")
public class CheckBiddingController {

    @Autowired
    private BiddingService biddingService;

    @PostMapping("/checkBiddingReport")
    public JSONObject checkBiddingReport(BiddingCondition biddingCondition, HttpSession session) {
        biddingCondition.setUid(Utils.uidFromSession(session));
//        JSONObject result = biddingService.biddingReport(biddingCondition);
        JSONObject result = biddingService.checkBiddingReport(biddingCondition);
        return result;
    }

    /**
     * @Desc:   审核竞标单
     * @Author: yanghm
     * @Param:  status=0审核通过
     * @Date:   10:10 2018/6/15 0015
     * @Return:
     */
    @PutMapping("/{status}/check")
    public ResponseEntity checkBidding(@RequestBody List<String> bidNames, @PathVariable("status") int status) {
        int count = biddingService.checkBidding(bidNames, status);
        return ResponseEntity.ok(count);
    }

}
