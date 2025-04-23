package com.example.fossupport.model.entity;


import com.example.fossupport.model.enums.ChatSupportStatus;
import com.example.fossupport.model.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
@Document(collection = "chats")
public class ChatSupport {

    @Id
    private String id;
    private long customerId;
    private long agentId;

    private ChatSupportStatus status;
    private List<Message> messages = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
    private boolean isDeleted;
    private LocalDateTime deletedAt;

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(fluent = true)
    public static class Message {

        private String chatId;
        private long senderId;
        private String senderName;
        private String senderRole;
        private String content;
        private MessageStatus status;
        private List<String> attachments = new ArrayList<>();
        private LocalDateTime createdAt;
    }
}
