package com.show.admin.scetc.pojo;

import lombok.Data;

import java.util.Date;
@Data
public class Orders {
    private  String id;
    private String GoodId;
    private String User_id;
    private String address;
    private String name;
    private String telephone;
    private String status;
    private Date createTime;
}
