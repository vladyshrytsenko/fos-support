package com.example.fossupport.controller;

import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.service.ChatSupportService;
import com.example.fossupport.service.impl.ChatSupportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chat")
    public void processMessage(ChatSupport.Message message) {
        ChatSupport savedMessage = chatSupportService.sendMessage(message);

        // Send back to clients - for example by chatId
        this.messagingTemplate.convertAndSend("/user/chat/" + message.chatId(), savedMessage);
    }

//    @GetMapping("/messages/{senderId}/{recipientId}/count")
//    public ResponseEntity<Long> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId) {
//
//        return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId));
//    }

//    @GetMapping("/messages/{senderId}/{recipientId}")
//    public ResponseEntity<?> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId) {
//        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
//    }
//
//    @GetMapping("/messages/{id}")
//    public ResponseEntity<?> findMessage( @PathVariable String id) {
//        return ResponseEntity.ok(chatMessageService.findById(id));
//    }

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatSupportService chatSupportService;
}
