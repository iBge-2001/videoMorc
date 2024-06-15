package com.show.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Orders {
    private String id;
    private String good_id;
    private String user_id;
    private String name;
    private String address;
    private String status;
    private String telephone;
    private Date create_time;

}
