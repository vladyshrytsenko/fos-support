package com.example.fossupport.service;

import com.example.fossupport.model.dto.UserDto;
import com.example.fossupport.model.entity.ChatSupport;

import java.util.List;

public interface ChatSupportService {

    ChatSupport create(UserDto user, String text, List<String> attachments);
    ChatSupport sendMessage(ChatSupport.Message message);
    List<ChatSupport> findAllChatsForCustomer(long userId);
    ChatSupport findByCustomerIdAndAgentId(long customerId, long agentId);
    ChatSupport getById(String chatId);
    List<ChatSupport> findUnassignedChats();
    ChatSupport assignSupportAgent(String chatId, long agentId);
    List<ChatSupport> findAllChatsHandledByAgent(long agentId);
    ChatSupport markChatAsResolved(String chatId);
    void deleteChat(String chatId);
}
