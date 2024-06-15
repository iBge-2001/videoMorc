package com.show.Wrapper;

import com.show.vo.ChatVo;

import java.util.List;

public class MessagesWrapper {
    private List<ChatVo> messages;

    // Getters and Setters
    public List<ChatVo> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatVo> messages) {
        this.messages = messages;
    }
}
