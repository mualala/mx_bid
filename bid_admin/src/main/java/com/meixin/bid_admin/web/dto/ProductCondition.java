package com.meixin.bid_admin.web.dto;

/**
 * @Desc： 产品的查询对象
 * @Author： yanghm
 * @Date： 09:51 2018/5/17 0017
 */
public class ProductCondition extends BasePageCondition {

    private String productTypeId;

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

}
