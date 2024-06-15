package com.show.controller;

import com.show.Wrapper.MessagesWrapper;
import com.show.pojo.Conversation;
import com.show.pojo.Message;
import com.show.pojo.Users;
import com.show.service.MessageService;
import com.show.service.UserService;
import com.show.utils.LybJsonResult;
import com.show.vo.ChatVo;
import com.show.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    Users other = null;


    @PostMapping("/send-message")
    public LybJsonResult sendMessage(@RequestBody Message message) {

        boolean flag = messageService.sendMessage(message);
        return new LybJsonResult(500,flag? "发送成功" : "发送成功",message);
    }

//    @GetMapping("/{conversationId}")
//    public List<Message> getMessagesByConversationId(@PathVariable String conversationId) {
//        return messageService.findByConversationId(conversationId);
//    }
    // other endpoint
    @GetMapping("/getMessageById")
    public List<MessageVo> getConversationByUserId(String userId, String chatId) {
        List<MessageVo> messageList = messageService.findMessageList(userId,chatId);
    for(MessageVo message : messageList){
       if(message.getAcceptId().equals(userId)) {
           message.setMessageType("accept");
       }else {message.setMessageType("send");}
    }
        return messageList;
    }
    @GetMapping("/getMessageList/{userId}")
    public List<Message> getMessageList(@PathVariable String userId){
        List<Message> messageListByAccept = messageService.getMessageListByAccept(userId);
        for(Message message:messageListByAccept){
                Message lastContent = messageService.getLastContent(message.getFromId(), message.getAcceptId());
                message.setAcceptId(lastContent.getAcceptId());
                message.setContent(lastContent.getContent());
                message.setId(lastContent.getId());
                message.setRead(lastContent.isRead());
                message.setFromId(lastContent.getFromId());
                message.setTime(lastContent.getTime());

        }
        List<Message> messageListByFrom = messageService.getMessageListByFrom(userId);
        for(Message message:messageListByFrom){
                Message lastContent = messageService.getLastContent(message.getFromId(), message.getAcceptId());
                message.setAcceptId(lastContent.getAcceptId());
                message.setContent(lastContent.getContent());
                message.setId(lastContent.getId());
                message.setNotread(lastContent.getNotread()+message.getNotread());
                message.setFromId(lastContent.getFromId());
                message.setTime(lastContent.getTime());
                message.setRead(lastContent.isRead());

        }
        List<Message> mergedMessageList = new ArrayList<>();
        mergedMessageList.addAll(messageListByAccept);
        mergedMessageList.addAll(messageListByFrom);
        //按时间排序
        Collections.sort(mergedMessageList, Comparator.comparing(Message::getTime).reversed());
        List<Message> result = new ArrayList<>();
        for(Message message : mergedMessageList){
            boolean found = false;
            for (Message otherMessage : mergedMessageList) {
                if (message.getAcceptId().equals(otherMessage.getFromId())  && message.getFromId().equals(otherMessage.getAcceptId())) {
                    if (message.getTime().before(otherMessage.getTime())) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found) {
                result.add(message);
            }
        }
        for (Message message:result){
            if (userId.equals(message.getAcceptId())){
                other = userService.queryUserInfo(message.getFromId());
            }else {
                other =  userService.queryUserInfo(message.getAcceptId());
            }
            message.setOther(other);
        }
        return result;
    }
    @PostMapping("/update-read")
    public ResponseEntity<?> updateReadStatus(@RequestBody MessagesWrapper messagesWrapper) {
        List<ChatVo> messages = messagesWrapper.getMessages();
        for (ChatVo update : messages) {
            String messageId = update.getId();
            Boolean isread = update.getIsread();
            if (messageId != null && isread != null) {
                messageService.updateReadStatus(messageId, isread);
            }
        }
        return ResponseEntity.ok("Read statuses updated successfully.");
    }
    @PostMapping("/save")
    public ResponseEntity<String> saveMessage(@RequestBody Message message) {
        messageService.saveMessage(message);
        return new ResponseEntity<>("Message saved successfully", HttpStatus.CREATED);
    }
}