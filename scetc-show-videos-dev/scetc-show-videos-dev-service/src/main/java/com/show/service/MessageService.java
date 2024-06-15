package com.show.service;

import com.show.mapper.MessageMapper;
import com.show.pojo.Conversation;
import com.show.pojo.Message;
import com.show.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface MessageService {

    public Boolean sendMessage(Message message);

    List<Message> findByConversationId(String conversationId);
    List<MessageVo> findMessageList(String userId, String chatId);
    List<Message> getMessageListByAccept(String userId);
    List<Message> getMessageListByFrom(String userId);
    Message getLastContent(String fromId,String acceptID);
    void updateReadStatus(String messageId, Boolean isread);
    void saveMessage(Message message);
}
