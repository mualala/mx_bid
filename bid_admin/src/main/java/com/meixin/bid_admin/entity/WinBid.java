package com.meixin.bid_admin.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:10 2018/6/11 0011
 */
@Entity
@Table(name = "win_bid")
public class WinBid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "win_bid_id")
    private Integer winBidId;

    private Integer uid;

    @Column(name = "bid_detail_id")
    private Integer bidDetailId;

    private String bidName;

    private Integer productId;

    private Integer suid;

    @NotNull
    @NotBlank
    private String reason;

    private Timestamp createTime;

    private Timestamp updateTime;


    @Transient
    private Supplier supplier;

    @Transient
    private Bidding bidding;

    @Transient
    private Product product;

    @Transient
    private BidDetails bidDetails;



    public Integer getWinBidId() {
        return winBidId;
    }

    public void setWinBidId(Integer winBidId) {
        this.winBidId = winBidId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBidDetailId() {
        return bidDetailId;
    }

    public void setBidDetailId(Integer bidDetailId) {
        this.bidDetailId = bidDetailId;
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

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public BidDetails getBidDetails() {
        return bidDetails;
    }

    public void setBidDetails(BidDetails bidDetails) {
        this.bidDetails = bidDetails;
    }

}
