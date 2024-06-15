package com.show.mapper;

import com.show.pojo.UsersFans;
import com.show.utils.MyMapper;

import java.util.List;

public interface UserFansMapper extends MyMapper<UsersFans>{
    List<UsersFans> findByUserId(String fan_id);
}
