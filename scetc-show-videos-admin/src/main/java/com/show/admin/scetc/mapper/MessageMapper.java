package com.show.admin.scetc.mapper;

import com.show.admin.scetc.pojo.AdminUser;
import com.show.admin.scetc.pojo.Message;
import com.show.admin.scetc.utils.MyMapper;
import org.apache.ibatis.annotations.Insert;

public interface MessageMapper extends MyMapper<Message> {
    @Insert("INSERT INTO message (id,from_id,accept_id, content, time, isread) VALUES (#{id},#{fromId},#{acceptId} ,#{content},#{time},#{isRead})")
    void insertMessage(Message message);
}
