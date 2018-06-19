package com.meixin.bid_admin.web.dto;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:54 2018/6/6 0006
 */
public class WinBidCondition extends BasePageCondition {

    private String bidName;

    private Integer mark;

    private String username;

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
