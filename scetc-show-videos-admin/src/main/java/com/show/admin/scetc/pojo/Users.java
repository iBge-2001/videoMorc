package com.show.admin.scetc.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
@Data
@Table(name = "users")
public class Users {
    @Id
    private String id;
    private String username;
    private String password;
    private String faceImage;
    private String nickname;
    private Integer fansCounts;
    private Integer followCounts;
    private Integer receiveLikeCounts;
    private Integer status;


}
