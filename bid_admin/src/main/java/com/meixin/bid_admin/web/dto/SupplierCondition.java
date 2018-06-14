package com.meixin.bid_admin.web.dto;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:22 2018/5/22 0022
 */
public class SupplierCondition extends BasePageCondition {

    private String supplierTypeId;

    private String companyName;

    private String phone;


    public String getSupplierTypeId() {
        return supplierTypeId;
    }

    public void setSupplierTypeId(String supplierTypeId) {
        this.supplierTypeId = supplierTypeId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
