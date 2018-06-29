package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.service.AdminService;
import com.meixin.bid_admin.web.dto.AdminCondition;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * @Desc： admin模块
 * @Author： yanghm
 * @Date： 10:30 2018/5/14 0014
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @GetMapping("/{name}/unique")
    public ResponseEntity checkNameIsUnique(@PathVariable(name = "name") String name) {
        Admin admin = adminService.checkName(name);

        if(admin == null)
            return ResponseEntity.ok(SimpleResponse.OK("账号可用"));

        return ResponseEntity.ok(new SimpleResponse(409, "账号冲突"));
    }

    @PostMapping
    public ResponseEntity createAdmin(@Valid Admin admin, BindingResult errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);
        }

        admin.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = adminService.createUser(admin);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/adminReport")
    public JSONObject userReport(AdminCondition adminCondition, HttpSession session) {
        adminCondition.setUid(Utils.uidFromSession(session));
        JSONObject result = adminService.userReport(adminCondition);
        return result;
    }

    @DeleteMapping("/{ids}/admin")
    public ResponseEntity deleteProduct(@PathVariable(name = "ids", required = true) String ids, HttpSession session) {
        int count = 0;
        try {
            count = adminService.deleteUsers(ids, Utils.uidFromSession(session));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        }
        return ResponseEntity.ok(count);
    }

    @PutMapping("/one")
    public ResponseEntity putAdmin(Admin admin) {
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


    /************** 延时 **************/
    /* --------------  -------------- */
    @GetMapping("/delay")
    public ResponseEntity getDelay() {
        return ResponseEntity.ok(Utils.Delay.getDelay());
    }

    @PutMapping("/delay")
    public ResponseEntity putDelay(@RequestParam("delay") int delay) {
        Utils.Delay.setDelay(delay);
        return ResponseEntity.ok(Utils.Delay.getDelay());
    }


}
