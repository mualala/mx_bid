package com.meixin.bid.entity;

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
    private Float limitPrice;

    @Transient
    private Integer mark;

    @Transient
    private Timestamp endTime;

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

    public Float getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(Float limitPrice) {
        this.limitPrice = limitPrice;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
