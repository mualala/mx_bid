package com.meixin.bid.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @Desc： 产品
 * @Author： yanghm
 * @Date： 10:13 2018/5/14 0014
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotNull(message = "产品编码不能为null")
    @NotBlank
    private String code;

    @NotNull(message = "产品名字不能为null")
    @NotBlank
    private String name;

    @NotNull
    @Column(name = "product_type_id")
    private Integer productTypeId;

    @NotNull
    @NotBlank
    private String spec; //产品规格

    @NotNull
    @NotBlank
    private String unit; //产品单位

    @NotNull
    @Column(name = "max_unit_price")
    private Float maxUnitPrice; //最高单价

    @NotNull
    @NotBlank
    @Column(name = "default_gradient")
    private String defaultGradient; //缺省梯度

    @Column(name = "product_desc")
    private String productDesc; //产品描述

    @Column(name = "create_time")
    private Timestamp createTime; //

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "uid")
    private Integer uid; //admin用户id

    @Transient
    private ProductType productType;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getMaxUnitPrice() {
        return maxUnitPrice;
    }

    public void setMaxUnitPrice(Float maxUnitPrice) {
        this.maxUnitPrice = maxUnitPrice;
    }

    public String getDefaultGradient() {
        return defaultGradient;
    }

    public void setDefaultGradient(String defaultGradient) {
        this.defaultGradient = defaultGradient;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

}
