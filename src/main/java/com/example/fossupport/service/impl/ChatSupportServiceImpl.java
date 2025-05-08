package com.example.fossupport.service.impl;

import com.example.fossupport.exception.ResourceNotFoundException;
import com.example.fossupport.model.Message;
import com.example.fossupport.model.dto.TopicDto;
import com.example.fossupport.model.dto.UserDto;
import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.model.enums.ChatSupportStatus;
import com.example.fossupport.model.enums.MessageStatus;
import com.example.fossupport.repository.ChatSupportRepository;
import com.example.fossupport.service.ChatSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatSupportServiceImpl implements ChatSupportService {

    @Override
    public ChatSupport create(UserDto user, TopicDto topicDto) {
        Message message = Message.builder()
            .chatId(topicDto.getMessage().getChatId())
            .senderId(user.getId())
            .senderName(user.getFirstName() + " " + user.getLastName())
            .senderRole(user.getRole())
            .status(MessageStatus.DELIVERED)
            .content(topicDto.getMessage().getContent())
            .attachments(topicDto.getMessage().getAttachments())
            .createdAt(LocalDateTime.now())
            .build();

        String messageContent = message.getContent();
        ChatSupport chat = ChatSupport.builder()
            .id(topicDto.getMessage().getChatId())
            .customerId(user.getId())
            .status(ChatSupportStatus.OPEN)
            .subject(topicDto.getSubject())
            .lastMessagePreview(messageContent.substring(0, Math.min(messageContent.length(), 30)).concat("..."))
            .messages(Collections.singletonList(message))
            .createdAt(LocalDateTime.now())
            .build();

        return this.chatSupportRepository.save(chat);
    }

    @Override
    public ChatSupport sendMessage(Message message) {
        ChatSupport chatFoundById = this.getById(message.getChatId());

        message.setCreatedAt(LocalDateTime.now());

        String messageContent = message.getContent();
        chatFoundById.setLastMessagePreview(messageContent.substring(0, Math.min(messageContent.length(), 30)).concat("..."));
        chatFoundById.getMessages().add(message);
        chatFoundById.setUpdatedAt(LocalDateTime.now());
        return this.chatSupportRepository.save(chatFoundById);
    }

//    public long countNewMessages(long customerId, long agentId) {
//        return this.chatSupportRepository.countBySenderIdAndRecipientIdAndStatus(
//            customerId,
//            agentId,
//            MessageStatus.RECEIVED
//        );
//    }

    @Override
    public Page<ChatSupport> findAllChatsForCustomer(long userId, Pageable pageable) {
        return this.chatSupportRepository.findAllByCustomerId(userId, pageable);
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
    public Page<ChatSupport> findUnassignedChats(Pageable pageable) {
        return this.chatSupportRepository.findAllByStatus(ChatSupportStatus.OPEN, pageable);
    }

    @Override
    public ChatSupport assignSupportAgent(String chatId, long agentId) {
        ChatSupport chatFoundById = this.getById(chatId);

        chatFoundById.setAgentId(agentId);
        chatFoundById.setStatus(ChatSupportStatus.ASSIGNED);
        chatFoundById.setUpdatedAt(LocalDateTime.now());

        return this.chatSupportRepository.save(chatFoundById);
    }

    @Override
    public Page<ChatSupport> findAllChatsHandledByAgent(long agentId, Pageable pageable) {
        return this.chatSupportRepository.findAllByAgentId(agentId, pageable);
    }

    @Override
    public ChatSupport markChatAsResolved(String chatId) {
        ChatSupport chatFoundById = this.getById(chatId);

        chatFoundById.setStatus(ChatSupportStatus.CLOSED);
        chatFoundById.setUpdatedAt(LocalDateTime.now());

        return this.chatSupportRepository.save(chatFoundById);
    }

    @Override
    public void deleteChat(String chatId) {
        this.chatSupportRepository.deleteById(chatId);
    }

    private final ChatSupportRepository chatSupportRepository;
}
