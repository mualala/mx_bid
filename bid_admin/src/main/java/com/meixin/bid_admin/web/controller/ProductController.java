package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.service.ProductService;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import com.meixin.bid_admin.web.dto.ProductCondition;
import com.meixin.bid_admin.web.support.SimpleResponse;
import com.meixin.bid_admin.web.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 13:34 2018/5/14 0014
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @RolesAllowed("ROLE_PROD")
    public ResponseEntity createProduct(@Valid Product product, BindingResult errors, HttpSession session) {
        if(errors.hasErrors()) {
//            List<FieldError> ls  = errors.getFieldErrors();
//            SimpleResponse simpleResponse = new SimpleResponse("有空字段");
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);
        }

        product.setUid(Utils.uidFromSession(session));
        product.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = productService.createProduct(product);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/productReport")
    public JSONObject productReport(ProductCondition productCondition, HttpSession session) {
        productCondition.setUid(Utils.uidFromSession(session));
        JSONObject result = productService.productReport(productCondition);
        return result;
    }

    @DeleteMapping("/{ids}/product")
    public ResponseEntity deleteProduct(@PathVariable(name = "ids", required = true) String ids, HttpSession session) {
        int count = productService.deleteProducts(ids, Utils.uidFromSession(session));
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{productId}/one")
    public ResponseEntity putProduct(@PathVariable(name = "productId", required = true) int productId,
                             Product product,
                             HttpSession session) {
        product.setProductId(productId);
        product.setUid(Utils.uidFromSession(session));
        product.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        int count = productService.updateProductInfo(product);
        return ResponseEntity.ok(count);
    }


}
