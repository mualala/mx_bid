package com.meixin.bid_admin.web.dto;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:54 2018/6/6 0006
 */
public class BidDetailsCondition extends BasePageCondition {

    private String suName; //供应商名称

    private String bidName;

    private Integer count;

    private Float sum;

    private String prodName;

    public String getSuName() {
        return suName;
    }

    public void setSuName(String suName) {
        this.suName = suName;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
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

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

}
