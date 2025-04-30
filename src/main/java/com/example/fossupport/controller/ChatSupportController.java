package com.example.fossupport.controller;

import com.example.fossupport.model.Message;
import com.example.fossupport.model.dto.ChatSupportDto;
import com.example.fossupport.model.dto.TopicDto;
import com.example.fossupport.model.dto.UserDto;
import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.service.ChatSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@MessageMapping("/ws")
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatSupportController {

    // WebSocket
    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        ChatSupport savedMessage = this.chatSupportService.sendMessage(message);

        // Send back to clients - for example by chatId
        this.simpMessagingTemplate.convertAndSend("/user/chat/" + message.getChatId(), savedMessage);
    }

    //todo @MessageMapping user is typing...

    // REST
    @PostMapping
    public ResponseEntity<ChatSupportDto> createChat(
        @AuthenticationPrincipal Jwt jwt,
        @RequestBody TopicDto topicDto) {

        Long userId = jwt.getClaim("user_id");
        String username = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String role = jwt.getClaimAsString("role");

        UserDto currentUser = UserDto.builder()
            .id(userId)
            .username(username)
            .email(email)
            .role(role)
            .build();

        ChatSupport chat = this.chatSupportService.create(currentUser, topicDto);
        ChatSupportDto chatSupportDto = ChatSupportDto.toDto(chat);
        return ResponseEntity.ok(chatSupportDto);
    }

    //todo @GetMapping countNewMessages() - for customer and agent separately as well
    //todo @GetMapping showNotification() - with username and message preview
    //todo @PutMapping("/{chatId}/resolve")
    //todo @PutMapping("/{chatId}/assign")

    @GetMapping("/{id}")
    public ResponseEntity<ChatSupportDto> getChatById(@PathVariable String id) {
        ChatSupport chatSupport = this.chatSupportService.getById(id);
        return ResponseEntity.ok(ChatSupportDto.toDto(chatSupport));
    }

    @GetMapping("/customer")
    public ResponseEntity<?> findAllChatsForCustomer(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        Long customerId = jwt.getClaim("user_id");
        Page<ChatSupport> chatPage = this.chatSupportService.findAllChatsForCustomer(customerId, pageable);
        Page<ChatSupportDto> chatDtoPage = chatPage.map(ChatSupportDto::toDto);
        return ResponseEntity.ok(chatDtoPage);
    }

    @GetMapping("/agent")
    public ResponseEntity<?> findAllChatsForAgent(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        Long agentId = jwt.getClaim("user_id");
        Page<ChatSupport> chatPage = this.chatSupportService.findAllChatsHandledByAgent(agentId, pageable);
        Page<ChatSupportDto> chatDtoPage = chatPage.map(ChatSupportDto::toDto);
        return ResponseEntity.ok(chatDtoPage);    }

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatSupportService chatSupportService;
}
