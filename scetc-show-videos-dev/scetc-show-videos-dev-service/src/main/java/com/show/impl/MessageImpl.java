package com.show.impl;

import com.show.mapper.MessageMapper;
import com.show.pojo.Conversation;
import com.show.pojo.Message;
import com.show.service.MessageService;
import com.show.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageImpl implements MessageService {
    @Autowired
    private MessageMapper messageRepository;

    @Override
    public Boolean sendMessage(Message message) {
        message.setRead(false);
        return  messageRepository.insert(message)>0;
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }

    @Override
    public List<MessageVo> findMessageList(String userId, String chatId) {
        return messageRepository.queryMessageList(userId,chatId);
    }

    @Override
    public List<Message> getMessageListByAccept(String userId) {
        return messageRepository.getMessageListByAccept(userId);
    }

    @Override
    public List<Message> getMessageListByFrom(String userId) {
        return messageRepository.getMessageListByFrom(userId);
    }

    @Override
    public Message getLastContent(String fromId, String acceptID) {
        return messageRepository.getLastContent(fromId,acceptID);
    }
    @Override
    public void updateReadStatus(String messageId, Boolean isread) {
        messageRepository.updateReadStatus(messageId, isread);
    }
    @Override
    public void saveMessage(Message message) {
        messageRepository.insertMessage(message);
    }

}
