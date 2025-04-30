package com.example.fossupport.service;

import com.example.fossupport.model.Message;
import com.example.fossupport.model.dto.TopicDto;
import com.example.fossupport.model.dto.UserDto;
import com.example.fossupport.model.entity.ChatSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatSupportService {

    ChatSupport create(UserDto user, TopicDto topicDto);
    ChatSupport sendMessage(Message message);
    Page<ChatSupport> findAllChatsForCustomer(long userId, Pageable pageable);
    ChatSupport findByCustomerIdAndAgentId(long customerId, long agentId);
    ChatSupport getById(String chatId);
    Page<ChatSupport> findUnassignedChats(Pageable pageable);
    ChatSupport assignSupportAgent(String chatId, long agentId);
    Page<ChatSupport> findAllChatsHandledByAgent(long agentId, Pageable pageable);
    ChatSupport markChatAsResolved(String chatId);
    void deleteChat(String chatId);
}
