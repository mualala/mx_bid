package com.meixin.bid.web.dto;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:12 2018/5/25 0025
 */
public class BiddingCondition extends BasePageCondition {

    private String name; //标单名称

    private Integer status; //标单状态 0：发布状态     1：正在竞标中     2：结束

    private Integer type; //类别 1:草稿 2:垃圾箱

    private Integer suid; //供应商id

    private Integer mark;//供应商查看我的项目 时的产品出价排名

//    private Integer all = 0; //默认显示全部 (查询竞标单那些供应商可看、可出价)


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
