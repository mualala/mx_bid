package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.BiddingSupplier;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.init.RoleType;
import com.meixin.bid_admin.service.*;
import com.meixin.bid_admin.web.dto.BiddingCondition;
import com.meixin.bid_admin.entity.Bidding;
import com.meixin.bid_admin.task.BiddingTaskUtil;
import com.meixin.bid_admin.web.dto.ProductCondition;
import com.meixin.bid_admin.web.dto.SupplierCondition;
import com.meixin.bid_admin.web.support.SimpleResponse;
import com.meixin.bid_admin.web.support.Utils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import static com.meixin.bid_admin.init.RoleType.ROLE_CHECK;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 08:46 2018/5/24 0024
 */
@RestController
@RequestMapping("/bidding")
public class BiddingController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(BiddingController.class);

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierTypeService supplierTypeService;

    @Autowired
    private SupplierService supplierService;


    @GetMapping("/{name}/unique")
    public ResponseEntity checkNameIsUnique(@PathVariable(name = "name") String name) {
        List<Bidding> biddings = biddingService.getBiddingName(name);

        if(biddings == null || biddings.isEmpty())
            return ResponseEntity.ok(SimpleResponse.OK("竞标单名称可用"));

        return ResponseEntity.ok(new SimpleResponse(409, "竞标单名称冲突"));
    }

    @PostMapping
    public ResponseEntity createBidding(@RequestBody List<Bidding> biddings, HttpSession session) {
        for (Bidding bid : biddings) {
            try {
                bid.parseAndSetAllTime();
                bid.setUid(Utils.uidFromSession(session));
                bid.setStatus(3);//待审核
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
                return new ResponseEntity(SimpleResponse.ERROR(e.getMessage(), 0), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        int count = biddingService.createBidding(biddings);

        return ResponseEntity.ok(count);
    }

    @PostMapping("/biddingReport")
    public JSONObject biddingReport(BiddingCondition biddingCondition, HttpSession session) {
        biddingCondition.setUid(Utils.uidFromSession(session));
        JSONObject result = biddingService.biddingReport(biddingCondition);
        return result;
    }

    @DeleteMapping("/{names}/bidding")
    public ResponseEntity deleteProduct(@PathVariable(name = "names", required = true) String names, HttpSession session) {
        int count = biddingService.deleteBiddings(names, Utils.uidFromSession(session));
        if (count < 1) {
            return new ResponseEntity(new SimpleResponse("不能删除正在竞标或已结束的竞标单!"), HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{names}/release")
    public ResponseEntity biddingRelease(@PathVariable(name = "names", required = true) String names, HttpSession session) {
        int count = biddingService.biddingRelease(names, Utils.uidFromSession(session));
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{name}/bidding")
    public ResponseEntity productsByBiddingName(@PathVariable(name = "name") String name, HttpSession session) {
        List<Bidding> biddings = biddingService.getProductsByBiddingName(name, Utils.uidFromSession(session));
        //供应商约束列表
        List<BiddingSupplier> biddingSupplierList = biddingService.getSuppliersByBiddingName(name);

        JSONObject result = new JSONObject();
        result.put("biddings", biddings);
        result.put("biddingSuppliers", biddingSupplierList);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity modifyBidding(@RequestBody List<Bidding> biddings, HttpSession session) {
        try {
            for (Bidding bid : biddings) {
                bid.parseAndSetAllTime();
                bid.setUid(Utils.uidFromSession(session));
                bid.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity(SimpleResponse.ERROR(e.getMessage(), 0), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int count = biddingService.modifyBidding(biddings);
        return ResponseEntity.ok(count);
    }




    @GetMapping("/productTypeNameList")
    public ResponseEntity productTypeNameList(HttpSession session) {
        List<ProductType> productTypeNameList = productTypeService.getProductTypeNames(Utils.uidFromSession(session));
        return ResponseEntity.ok(productTypeNameList);
    }

    @PostMapping("/productReport")
    public JSONObject productReport(ProductCondition productCondition, HttpSession session) {
        productCondition.setUid(Utils.uidFromSession(session));
        JSONObject result = productService.productReport(productCondition);
        return result;
    }


    @PostMapping("/supplierReport")
    public JSONObject supplierReport(SupplierCondition supplierCondition, HttpSession session) {
        supplierCondition.setUid(Utils.uidFromSession(session));
        JSONObject result = supplierService.supplierReport(supplierCondition);
        return result;
    }

    @GetMapping("/supplierTypeNameList")
    public ResponseEntity supplierTypeNameList(HttpSession session) {
        List<SupplierType> supplierTypeNameList = supplierTypeService.getSupplierTypeNames(Utils.uidFromSession(session));
        return ResponseEntity.ok(supplierTypeNameList);
    }

}
