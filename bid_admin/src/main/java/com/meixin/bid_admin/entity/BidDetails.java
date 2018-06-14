package com.meixin.bid_admin.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:30 2018/6/4 0004
 */
@Entity
@Table(name = "bid_details")
public class BidDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_detail_id")
    private Integer bidDetailId;

    private Integer uid;

    @NotBlank
    @NotNull
    private String bidName;

    private Integer productId;

    private Float price;

    private Timestamp createTime;

    @Transient
    private Integer rank;

    @Transient
    private Integer count; //出价次数 or 参与竞标的产品数量

    @Transient
    private Float sum;

    @Transient
    private Supplier supplier;

    @Transient
    private Bidding bidding;

    @Transient
    private Product product;

    @Transient
    private Float optimal;//最优报价 最高或最低


    public Integer getBidDetailId() {
        return bidDetailId;
    }

    public void setBidDetailId(Integer bidDetailId) {
        this.bidDetailId = bidDetailId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Bidding getBidding() {
        return bidding;
    }

    public void setBidding(Bidding bidding) {
        this.bidding = bidding;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getOptimal() {
        return optimal;
    }

    public void setOptimal(Float optimal) {
        this.optimal = optimal;
    }

}
