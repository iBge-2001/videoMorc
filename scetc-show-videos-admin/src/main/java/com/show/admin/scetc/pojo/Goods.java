package com.show.admin.scetc.pojo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Goods {
    private String id;
    private String title;
    private String intro;
    private List<String> images;
    private String imgs;
    private Double price ;
    private String value;
    private String status;
    private String uid;
    private Date date;
    private Integer isCheck;
}
