package com.meixin.bid.web.controller;

import com.meixin.bid.entity.Supplier;
import com.meixin.bid.service.SupplierService;
import com.meixin.bid.web.support.SimpleResponse;
import com.meixin.bid.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionAttributeStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:39 2018/5/11 0011
 */
@Controller
@RequestMapping("/supplier")
public class SupplierController {
    private final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/checkPwd")
    public ResponseEntity checkPwd(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        boolean success = supplierService.modifyPwdPreCkeck(username, password);
        if (success) {
            return ResponseEntity.ok(SimpleResponse.OK("原始密码正确"));
        }
        return ResponseEntity.ok(SimpleResponse.ERROR("原始密码不正确"));
    }

    @PutMapping("/one")
    public ResponseEntity putProduct(Supplier supplier, HttpSession session) {
        int supplierId = (int) session.getAttribute("suid");
        supplier.setSupplierId(supplierId);
        supplier.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int count = 0;
        try {
            count = supplierService.updateSupplierInfo(supplier);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(count);
    }


}
