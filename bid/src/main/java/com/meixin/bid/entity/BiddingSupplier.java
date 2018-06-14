package com.meixin.bid.entity;

import javax.persistence.*;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 14:16 2018/6/12 0012
 */
@Entity
@Table(name = "bidding_supplier")
public class BiddingSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bidding_supplier_id")
    private Integer biddingSupplierId;

    private String bidName;

    private Integer suid; //供应商id

    private Supplier supplier;

    public Integer getBiddingSupplierId() {
        return biddingSupplierId;
    }

    public void setBiddingSupplierId(Integer biddingSupplierId) {
        this.biddingSupplierId = biddingSupplierId;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
