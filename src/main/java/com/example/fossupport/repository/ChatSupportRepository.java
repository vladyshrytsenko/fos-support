package com.example.fossupport.repository;

import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.model.enums.ChatSupportStatus;
import com.example.fossupport.model.enums.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatSupportRepository extends MongoRepository<ChatSupport, String> {

    Optional<ChatSupport> findByCustomerIdAndAgentId(long customerId, long agentId);
    List<ChatSupport> findAllByCustomerId(long customerId);
    List<ChatSupport> findAllByAgentId(long agentId);
    List<ChatSupport> findAllByStatus(ChatSupportStatus status);
//    int countByCustomerIdAndAgentIdAndStatus(String senderId, String recipientId, MessageStatus status);
    ChatSupport assignSupportAgent(String chatId, long agentId);
    void markChatAsResolved(String chatId);
}
