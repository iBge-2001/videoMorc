package com.show.admin.scetc.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "accept_id")
    private String acceptId;
    @Column(name = "from_id")
    private String fromId;

//    @JoinColumn(name = "sender_id")
//    private Users sender;
//
//    @JoinColumn(name = "receiver_id")
//    private Users receiver;

    private  Users other;
    @Column(name = "content")
    private String content;
    private Date time;
    @Column(name = "isread")
    private Boolean isread;
    @Column(name = "notread")
    private int notread;





//    public Users getSender() {
//        return sender;
//    }
//
//    public void setSender(Users sender) {
//        this.sender = sender;
//    }
//
//    public Users getReceiver() {
//        return receiver;
//    }
//
//    public void setReceiver(Users receiver) {
//        this.receiver = receiver;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    private boolean isRead;
    // other fields and getters/setters
}
