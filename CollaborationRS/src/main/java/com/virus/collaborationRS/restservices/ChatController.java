package com.virus.collaborationRS.restservices;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virus.collaborationBE.chat.Message;
import com.virus.collaborationBE.chat.OutputMessage;

@Controller
@RequestMapping("/")
public class ChatController {
    
  @MessageMapping("/chat")
  @SendTo("/topic/message")
  public OutputMessage sendMessage(Message message) {
	  System.out.println(message.getMsgid());
    return new OutputMessage(message, new Date());
  }
}
