package com.meixin.bid_admin.web.dto;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:12 2018/5/25 0025
 */
public class BiddingCondition extends BasePageCondition {

    private String name; //标单名称

    private Integer mark;

    private Integer status; //标单状态 0：发布状态     1：正在竞标中     2：结束

    private Integer type; //类别 1:草稿 2:垃圾箱

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
