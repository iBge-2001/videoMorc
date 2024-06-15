package com.show.admin.scetc.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.show.admin.scetc.pojo.UserConsole;
import com.show.admin.scetc.pojo.Users;

import java.util.List;

public interface UserService  {
    public Boolean update1(String id,Integer status);

    List<Users> list();

    boolean remove(String Id);


    Boolean update(String id, String password);

    List<UserConsole> getUserConsole();
}

