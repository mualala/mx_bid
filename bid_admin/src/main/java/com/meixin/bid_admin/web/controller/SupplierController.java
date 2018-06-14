package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.service.AdminService;
import com.meixin.bid_admin.service.SupplierService;
import com.meixin.bid_admin.web.dto.ProductCondition;
import com.meixin.bid_admin.web.dto.SupplierCondition;
import com.meixin.bid_admin.web.support.SimpleResponse;
import com.meixin.bid_admin.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:39 2018/5/11 0011
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/{username}/unique")
    public ResponseEntity checkNameIsUnique(@PathVariable(name = "username") String username) {
        Supplier supplier = supplierService.getSupplier(username);

        if(supplier == null)
            return ResponseEntity.ok(SimpleResponse.OK("该用户名可用"));

        return ResponseEntity.ok(new SimpleResponse(409, "该用户名不可用"));
    }

    @PostMapping
    @RolesAllowed("ROLE_USER")
    public ResponseEntity createSupplier(@Valid Supplier supplier, BindingResult errors, HttpSession session) {
        if(errors.hasErrors())
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);

        supplier.setUid(Utils.uidFromSession(session));
        supplier.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = supplierService.createSupplier(supplier);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/supplierReport")
    public JSONObject productReport(SupplierCondition supplierCondition, HttpSession session) {
        supplierCondition.setUid(Utils.uidFromSession(session));
        JSONObject result = supplierService.supplierReport(supplierCondition);
        return result;
    }

    @DeleteMapping("/{ids}/supplier")
    public ResponseEntity deleteProduct(@PathVariable(name = "ids", required = true) String ids, HttpSession session) {
        int count = supplierService.deleteSuppliers(ids, Utils.uidFromSession(session));
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{supplierId}/one")
    public ResponseEntity putProduct(@PathVariable(name = "supplierId", required = true) int supplierId,
                                     Supplier supplier,
                                     HttpSession session) {
        supplier.setSupplierId(supplierId);
        supplier.setUid(Utils.uidFromSession(session));
        supplier.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        int count = supplierService.updateSupplierInfo(supplier);
        return ResponseEntity.ok(count);
    }

    /**
     * @Desc:   原始密码检查   写在这儿了...
     * @Author: yanghm
     * @Param:
     * @Date:   16:39 2018/5/30 0030
     * @Return:
     */
    @PostMapping("/checkPwd")
    public ResponseEntity checkPwd(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        boolean success = adminService.modifyPwdPreCkeck(username, password);
        if (success) {
            return ResponseEntity.ok(SimpleResponse.OK("原始密码正确"));
        }
        return ResponseEntity.ok(SimpleResponse.ERROR("原始密码不正确"));
    }

    @PutMapping("/one")
    public ResponseEntity putProduct(Admin admin, HttpSession session) {
        int uid = Utils.uidFromSession(session);
        if (uid != -1) {
            admin.setUid(uid);
        }else {
            admin.setUid(adminService.checkName(admin.getUsername()).getUid());
        }
        admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int count = 0;
        try {
            count = adminService.updateUserInfo(admin);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(count);
    }

}
