package com.show.mapper;

import com.show.pojo.Conversation;
import com.show.pojo.Message;
import com.show.utils.MyMapper;
import com.show.vo.MessageVo;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;


import java.util.List;

public interface MessageMapper extends MyMapper<Message>{
    @Select(value = "select * from message where conversation_id = #{conversationId}")
    List<Message> findByConversationId(String conversationId);
    @Select(value = "SELECT * FROM message WHERE\n" +
            "\tmessage.accept_id = #{userId} AND\n" +
            "\tmessage.from_id = #{chatId} OR\n" +
            "\tmessage.from_id = #{userId} AND\n" +
            "\tmessage.accept_id = #{chatId}\n" +
            "ORDER BY message.time ASC")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "fromId",column = "from_id"),
            @Result(property = "acceptId",column = "accept_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "time",column = "time"),
            @Result(property = "isread",column = "isread")
    })
    List<MessageVo> queryMessageList(@Param("userId")String userId, @Param("chatId")String chatId);
    @Select(value = "SELECT SUM(not message.isread)as notread, message.id, message.from_id,message.accept_id, message.content, message.time FROM message where accept_id = #{userId}GROUP BY message.from_id")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "fromId",column = "from_id"),
            @Result(property = "acceptId",column = "accept_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "time",column = "time"),
            @Result(property = "isread",column = "isread"),
            @Result(property = "sum",column = "sum")
    })
    List<Message> getMessageListByAccept(String userId);
    @Select(value = "SELECT message.*\n" +
            "FROM message\n" +
            "WHERE message.from_id = #{fromId} AND message.accept_id = #{acceptID} ORDER BY message.time DESC LIMIT 1")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "fromId",column = "from_id"),
            @Result(property = "acceptId",column = "accept_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "time",column = "time"),
            @Result(property = "isread",column = "isread"),
            @Result(property = "sum",column = "sum")
    })
    Message getLastContent(@org.apache.ibatis.annotations.Param("fromId") String fromId, @Param("acceptID") String acceptID)throws DataAccessException;
    @Select(value = "SELECT SUM(not message.isread) as notread, message.id,message.from_id, message.accept_id,message.content, message.time FROM message where from_id = #{userId}GROUP BY message.accept_id")
    @Results({
            @Result(id = true, property = "id",column = "id"),
            @Result(property = "fromId",column = "from_id"),
            @Result(property = "acceptId",column = "accept_id"),
            @Result(property = "content",column = "content"),
            @Result(property = "time",column = "time"),
            @Result(property = "isread",column = "isread"),
            @Result(property = "sum",column = "sum")
    })
    List<Message> getMessageListByFrom(String userId);
    @Update("UPDATE message SET isread = #{isread} WHERE id = #{id}")
    int updateReadStatus(@Param("id") String id, @Param("isread") Boolean isread);
    @Insert("INSERT INTO message (from_id, content, time, is_read) VALUES (#{fromId}, #{content}, #{time}, #{isRead})")
    void insertMessage(Message message);
}
