package com.show.vo;

import com.show.pojo.Message;
import lombok.Data;

@Data
public class MessageVo  extends Message{


    //发送id时user则Type是send，接收id是user则type是accept
    private String messageType;


}
