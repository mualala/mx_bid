package com.meixin.bid_admin.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Desc： 竞标单
 * @Author： yanghm
 * @Date： 10:20 2018/5/14 0014
 */
@Entity
@Table(name = "bidding")
public class Bidding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private String originalName; //修改竞标单数据时用

    private String name; //标单名称

    private Integer productId; //产品id

    private Integer number; //参与投标产品的数量

    private Float startPrice; //起拍价

    private Float step; //竞价阶梯(元)

    @NotNull
    @Column(name = "end_delivery_date")
    private Timestamp endDeliveryDate; //交货期限

    @NotNull
    @Column(name = "end_pay_date")
    private Timestamp endPayDate; //付款期限

    private Integer mark; //1=招标（公司卖）   2=竞标（公司买）

    private Integer status = 0; //标单状态 0：发布状态  1：正在竞标中  2：结束  3:审核

    private Integer type; //类别 1:草稿 2:垃圾箱

    private String bidDesc; //详细信息

    private Timestamp startTime; //启标日期

    private Timestamp endTime; //竞标单截止日期

    private Timestamp createTime;

    private Timestamp updateTime;

    private Integer uid; //admin用户id

    private Integer finish = 0; // 选择中标完成状态 1:完成

    private String taskName; //竞标单triggerKey的name

    private String groupId; //竞标单triggerKey的group

    @Transient
    private Integer count;

    @Transient
    private Product product;

    @Transient
    private String recSuppliers;
//    private List<BiddingSupplier> biddingSupplier;

    /* 用于接收前端的日期数据 */
    @Transient
    @NotNull
    @NotBlank
    private String recEndDeliveryDate;

    @Transient
    @NotNull
    @NotBlank
    private String recEndPayDate;

    @Transient
    @NotNull
    @NotBlank
    private String recStartTime;

    @Transient
    @NotNull
    @NotBlank
    private String recEndTime;


    /**
     * @Desc:   转换前端传过来的时间数据， 并且设置创建时间
     * @Author: yanghm
     * @Param:
     * @Date:   11:37 2018/5/24 0024
     * @Return:
     */
    public void parseAndSetAllTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (StringUtils.isBlank(this.recStartTime) || StringUtils.isBlank(this.recEndTime)) {
            throw new ParseException("竞标单开启时间 or 竞标单截止时间：为空!!", 0);
        }

        Date rst = sdf.parse(this.recStartTime);
        Date ret = sdf.parse(this.recEndTime);
        if(rst.getTime() >= ret.getTime()) {
            throw new ParseException("竞标单开启时间 大于或等于 竞标单截止时间", 0);
        }

        if (!StringUtils.isBlank(this.recEndDeliveryDate)) {
            Date redd = sdf.parse(this.recEndDeliveryDate);
            this.setEndDeliveryDate(new Timestamp(redd.getTime()));
        }
        if (!StringUtils.isBlank(this.recEndPayDate)) {
            Date repd = sdf.parse(this.recEndPayDate);
            this.setEndPayDate(new Timestamp(repd.getTime()));
        }
        if (!StringUtils.isBlank(this.recStartTime)) {
            this.setStartTime(new Timestamp(rst.getTime()));
        }
        if (!StringUtils.isBlank(this.recEndTime)) {
            this.setEndTime(new Timestamp(ret.getTime()));
        }

        this.setCreateTime(new Timestamp(System.currentTimeMillis()));
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Float startPrice) {
        this.startPrice = startPrice;
    }

    public Float getStep() {
        return step;
    }

    public void setStep(Float step) {
        this.step = step;
    }

    public Timestamp getEndDeliveryDate() {
        return endDeliveryDate;
    }

    public void setEndDeliveryDate(Timestamp endDeliveryDate) {
        this.endDeliveryDate = endDeliveryDate;
    }

    public Timestamp getEndPayDate() {
        return endPayDate;
    }

    public void setEndPayDate(Timestamp endPayDate) {
        this.endPayDate = endPayDate;
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

    public String getBidDesc() {
        return bidDesc;
    }

    public void setBidDesc(String bidDesc) {
        this.bidDesc = bidDesc;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getRecSuppliers() {
        return recSuppliers;
    }

    public void setRecSuppliers(String recSuppliers) {
        this.recSuppliers = recSuppliers;
    }

    /* 用于接收前端的日期数据 */
    public String getRecEndDeliveryDate() {
        return recEndDeliveryDate;
    }
    public void setRecEndDeliveryDate(String recEndDeliveryDate) {
        this.recEndDeliveryDate = recEndDeliveryDate;
    }
    public String getRecEndPayDate() {
        return recEndPayDate;
    }
    public void setRecEndPayDate(String recEndPayDate) {
        this.recEndPayDate = recEndPayDate;
    }
    public String getRecStartTime() {
        return recStartTime;
    }
    public void setRecStartTime(String recStartTime) {
        this.recStartTime = recStartTime;
    }

    public String getRecEndTime() {
        return recEndTime;
    }
    public void setRecEndTime(String recEndTime) {
        this.recEndTime = recEndTime;
    }




}
