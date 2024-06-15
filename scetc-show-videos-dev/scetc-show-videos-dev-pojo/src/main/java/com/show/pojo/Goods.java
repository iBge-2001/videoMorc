package com.show.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;


public class Goods {
    @Id
    private String id;
    @Column(name = "imgs")
    private String imgs;
    @Column(name = "title")
    private String title;
    @Column(name = "intro")
    private String intro;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "value")
    private String value;
    @Column(name = "date")
    private java.util.Date date;
    @Column(name = "status")
    private String status;
    @Column(name = "uid")
    private String uid;
    @Column(name="is_check")
    private Integer  isCheck;

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }
    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                ", imgs='" + imgs + '\'' +
                ", title='" + title + '\'' +
                ", intro='" + intro + '\'' +
                ", price=" + price +
                ", value='" + value + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", uid='" + uid + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }




    public Goods() {

    }

    public String getuId() {
        return uid;
    }

    public void setuId(String uId) {
        this.uid = uId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Goods(String id,String goodImgs, String title,String introText,BigDecimal price,String value,Date date,String saleStatus,String userId) {
        this.id = id;
        this.status = saleStatus;
        this.price = price;
        this.imgs = goodImgs;
        this.intro = introText;
        this.date = date;
        this.value = value;
        this.title = title;
        this.uid = userId;
    }

    public Goods(String id) {
        this.id = id;
    }

}
