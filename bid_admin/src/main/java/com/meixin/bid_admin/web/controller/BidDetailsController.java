package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.BidDetails;
import com.meixin.bid_admin.entity.WinBid;
import com.meixin.bid_admin.service.BidDetailsService;
import com.meixin.bid_admin.web.dto.BidDetailsCondition;
import com.meixin.bid_admin.web.dto.SupplierCondition;
import com.meixin.bid_admin.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:52 2018/6/6 0006
 */
@RestController
@RequestMapping("/bidDetails")
public class BidDetailsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BidDetailsController.class);

    @Autowired
    private BidDetailsService bidDetailsService;

    @PostMapping("/bidDetailsReport")
    public JSONObject bidDetailsReport(BidDetailsCondition condition, HttpSession session) {
        condition.setUid(Utils.uidFromSession(session));
        JSONObject result = bidDetailsService.bidDetailsReport(condition);
        return result;
    }

    @GetMapping("/{name}/optimal")
    public ResponseEntity optimalPrice(@PathVariable("name") String bidName) {
        List<BidDetails> optimalPrices = bidDetailsService.optimalPrice(bidName);

        if (optimalPrices == null) {
            return new ResponseEntity("竞标单标记错误", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(optimalPrices);
    }

    /**
     * @Desc:   根据 标单名称+产品id得到每个供应商的最优出价
     * @Author: yanghm
     * @Param:
     * @Date:   16:58 2018/6/8 0008
     * @Return:
     */
    @GetMapping("/{name}/{productId}/optimalDetail")
    public ResponseEntity allSupplierOptimalPriceDetail(@PathVariable("name") String bidName, @PathVariable("productId") int productId) {
        List<BidDetails> optimalPrices = bidDetailsService.allSupplierOptimalPriceDetail(bidName, productId);
        if (optimalPrices == null) {
            return new ResponseEntity("竞标单标记错误", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(optimalPrices);
    }

    @PostMapping("/priceDetails")
    public ResponseEntity priceDetails(BidDetailsCondition condition, HttpSession session) {
        condition.setUid(Utils.uidFromSession(session));
        JSONObject result = bidDetailsService.priceDetails(condition);
        return ResponseEntity.ok(result);
    }



}
