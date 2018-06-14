package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.service.ProductTypeService;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import com.meixin.bid_admin.web.support.SimpleResponse;
import com.meixin.bid_admin.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:19 2018/5/18 0018
 */
@RestController
@RequestMapping("/productType")
public class ProductTypeController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductTypeController.class);
    
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("/{name}/unique")
    public ResponseEntity checkNameIsUnique(@PathVariable(name = "name") String name) {
        ProductType productType = productTypeService.getProductType(name);

        if(productType == null)
            return ResponseEntity.ok(SimpleResponse.OK("产品类型名称可用"));

        return ResponseEntity.ok(new SimpleResponse(409, "产品类型名称冲突"));
    }

    @PostMapping
    public ResponseEntity createProductType(@Valid ProductType productType, BindingResult errors, HttpSession session) {
        if(errors.hasErrors()) {
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);
        }

        productType.setUid(Utils.uidFromSession(session));
        productType.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = productTypeService.createProductType(productType);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/productTypeReport")
    public JSONObject productTypeReport(BasePageCondition condition, HttpSession session) {
        condition.setUid(Utils.uidFromSession(session));
        return productTypeService.getProductTypeReport(condition);
    }

    @DeleteMapping("/{ids}/productType")
    public ResponseEntity deleteProductType(@PathVariable(name = "ids", required = true) String ids, HttpSession session) {
        int count = 0;
        try {
            count = productTypeService.deleteProductTypes(ids, Utils.uidFromSession(session));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{productTypeId}/one")
    public ResponseEntity putProduct(@PathVariable(name = "productTypeId", required = true) int productTypeId,
                                     ProductType productType,
                                     HttpSession session) {
        productType.setProductTypeId(productTypeId);
        productType.setUid(Utils.uidFromSession(session));
        productType.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        int count = productTypeService.updateProductTypeInfo(productType);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/productTypeNameList")
    public ResponseEntity productTypeNameList(HttpSession session) {
        List<ProductType> productTypeNameList = productTypeService.getProductTypeNames(Utils.uidFromSession(session));
        return ResponseEntity.ok(productTypeNameList);
    }


}
