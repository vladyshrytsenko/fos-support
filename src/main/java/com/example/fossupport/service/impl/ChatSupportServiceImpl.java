package com.example.fossupport.service.impl;

import com.example.fossupport.exception.ResourceNotFoundException;
import com.example.fossupport.model.dto.UserDto;
import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.model.enums.ChatSupportStatus;
import com.example.fossupport.model.enums.MessageStatus;
import com.example.fossupport.repository.ChatSupportRepository;
import com.example.fossupport.service.ChatSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatSupportServiceImpl implements ChatSupportService {

    @Override
    public ChatSupport create(UserDto user, String text, List<String> attachments) {
        ChatSupport.Message message = ChatSupport.Message.builder()
            .senderId(user.id())
            .senderName(user.username())
            .senderRole(user.role())
            .status(MessageStatus.DELIVERED)
            .content(text)
            .attachments(attachments)
            .createdAt(LocalDateTime.now())
            .build();

        ChatSupport chat = ChatSupport.builder()
            .customerId(user.id())
            .status(ChatSupportStatus.OPEN)
            .messages(Collections.singletonList(message))
            .createdAt(LocalDateTime.now())
            .build();

        return this.chatSupportRepository.save(chat);
    }

    @Override
    public ChatSupport sendMessage(ChatSupport.Message message) {
        ChatSupport foundChat = this.getById(message.chatId());

        message.createdAt(LocalDateTime.now());

        foundChat.messages().add(message);
        foundChat.updatedAt(LocalDateTime.now());
        this.chatSupportRepository.save(foundChat);

        return foundChat;
    }

//    public long countNewMessages(long customerId, long agentId) {
//        return this.chatSupportRepository.countBySenderIdAndRecipientIdAndStatus(
//            customerId,
//            agentId,
//            MessageStatus.RECEIVED
//        );
//    }

    @Override
    public List<ChatSupport> findAllChatsForCustomer(long userId) {
        return this.chatSupportRepository.findAllByCustomerId(userId);
    }

    @Override
    public ChatSupport findByCustomerIdAndAgentId(long customerId, long agentId) {
        return this.chatSupportRepository.findByCustomerIdAndAgentId(customerId, agentId)
            .orElse(null);
    }

    @Override
    public ChatSupport getById(String chatId) {
        return this.chatSupportRepository.findById(chatId)
            .orElseThrow(() -> new ResourceNotFoundException("Chat by id: "+chatId+" not found"));
    }

    @Override
    public List<ChatSupport> findUnassignedChats() {
        return this.chatSupportRepository.findAllByStatus(ChatSupportStatus.OPEN);
    }

    @Override
    public ChatSupport assignSupportAgent(String chatId, long agentId) {
        ChatSupport foundChat = this.getById(chatId);
        foundChat.agentId(agentId);
        foundChat.status(ChatSupportStatus.ASSIGNED);
        foundChat.updatedAt(LocalDateTime.now());

        ChatSupport updatedChat = this.chatSupportRepository.save(foundChat);
        return updatedChat;
    }

    @Override
    public List<ChatSupport> findAllChatsHandledByAgent(long agentId) {
        return this.chatSupportRepository.findAllByAgentId(agentId);
    }

    @Override
    public ChatSupport markChatAsResolved(String chatId) {
        ChatSupport foundChat = this.getById(chatId);
        foundChat.status(ChatSupportStatus.CLOSED);
        foundChat.updatedAt(LocalDateTime.now());

        ChatSupport updatedChat = this.chatSupportRepository.save(foundChat);
        return updatedChat;
    }

    @Override
    public void deleteChat(String chatId) {
        this.chatSupportRepository.deleteById(chatId);
    }

    private final ChatSupportRepository chatSupportRepository;
}
