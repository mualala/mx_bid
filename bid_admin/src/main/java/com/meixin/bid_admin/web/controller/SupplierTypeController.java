package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.service.SupplierTypeService;
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
 * @Date： 09:04 2018/5/22 0022
 */
@RestController
@RequestMapping("/supplierType")
public class SupplierTypeController {

    private final Logger LOGGER = LoggerFactory.getLogger(SupplierTypeController.class);

    @Autowired
    private SupplierTypeService supplierTypeService;

    @GetMapping("/{name}/unique")
    public ResponseEntity checkNameIsUnique(@PathVariable(name = "name") String name) {
        SupplierType supplierType = supplierTypeService.getSupplierType(name);

        if(supplierType == null)
            return ResponseEntity.ok(SimpleResponse.OK("供应商类型名称可用"));

        return ResponseEntity.ok(new SimpleResponse(409, "供应商类型名称冲突"));
    }

    @PostMapping
    public ResponseEntity createSupplierType(@Valid SupplierType supplierType, BindingResult errors, HttpSession session) {
        if(errors.hasErrors()) {
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);
        }

        supplierType.setUid(Utils.uidFromSession(session));
        supplierType.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = supplierTypeService.createSupplierType(supplierType);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/supplierTypeReport")
    public JSONObject productTypeReport(BasePageCondition condition, HttpSession session) {
        condition.setUid(Utils.uidFromSession(session));
        return supplierTypeService.getSupplierTypeReport(condition);
    }

    @DeleteMapping("/{ids}/supplierType")
    public ResponseEntity deleteProductType(@PathVariable(name = "ids", required = true) String ids, HttpSession session) {
        int count = 0;
        try {
            count = supplierTypeService.deleteSupplierTypes(ids, Utils.uidFromSession(session));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{supplierTypeId}/one")
    public ResponseEntity putProduct(@PathVariable(name = "supplierTypeId", required = true) int supplierTypeId,
                                     SupplierType supplierType,
                                     HttpSession session) {
        supplierType.setSupplierTypeId(supplierTypeId);
        supplierType.setUid(Utils.uidFromSession(session));
        supplierType.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        int count = supplierTypeService.updateSupplierTypeInfo(supplierType);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/supplierTypeNameList")
    public ResponseEntity productTypeNameList(HttpSession session) {
        List<SupplierType> supplierTypeNameList = supplierTypeService.getSupplierTypeNames(Utils.uidFromSession(session));
        return ResponseEntity.ok(supplierTypeNameList);
    }


}
