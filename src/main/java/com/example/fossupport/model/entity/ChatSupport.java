package com.example.fossupport.model.entity;

import com.example.fossupport.model.Message;
import com.example.fossupport.model.enums.ChatSupportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chats")
public class ChatSupport {

    @Id
    private String id;

    @Field(name = "customer_id")
    private long customerId;
    
    @Field(name = "agent_id")
    private long agentId;

    @Field(name = "status")
    private ChatSupportStatus status;

    @Field(name = "subject")
    private String subject;

    @Field(name = "last_message_preview")
    private String lastMessagePreview;

    @Field(name = "messages")
    private List<Message> messages = new ArrayList<>();

    @Field(name = "created_at")
    private LocalDateTime createdAt;

    @Field(name = "updated_at")
    private LocalDateTime updatedAt;

    @Field(name = "closed_at")
    private LocalDateTime closedAt;

    @Field(name = "is_deleted")
    private boolean isDeleted;

    @Field(name = "deleted_at")
    private LocalDateTime deletedAt;
}
