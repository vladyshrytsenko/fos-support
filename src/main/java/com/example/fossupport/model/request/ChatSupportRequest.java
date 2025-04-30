package com.example.fossupport.model.request;

import com.example.fossupport.model.Message;
import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.model.enums.ChatSupportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatSupportRequest {

    private long customerId;
    private long agentId;

    private String status;
    private List<Message> messages;

    public static ChatSupport toEntity(ChatSupportRequest request) {
        return ChatSupport.builder()
            .customerId(request.getCustomerId())
            .agentId(request.getAgentId())
            .status(ChatSupportStatus.valueOf(request.getStatus()))
            .messages(request.getMessages())
            .build();
    }
}
