package com.example.fossupport.model.dto;

import com.example.fossupport.model.Message;
import com.example.fossupport.model.entity.ChatSupport;
import com.example.fossupport.model.enums.ChatSupportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class ChatSupportDto {

    private String id;
    private long customerId;
    private long agentId;
    private String status;
    private String subject;
    private List<Message> messages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
    private boolean isDeleted;
    private LocalDateTime deletedAt;

    public static ChatSupportDto toDto(ChatSupport entity) {
        return ChatSupportDto.builder()
            .id(entity.getId())
            .customerId(entity.getCustomerId())
            .agentId(entity.getAgentId())
            .status(entity.getStatus().name())
            .subject(entity.getSubject())
            .messages(entity.getMessages())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .closedAt(entity.getClosedAt())
            .isDeleted(entity.isDeleted())
            .deletedAt(entity.getDeletedAt())
            .build();
    }

    public static ChatSupport toEntity(ChatSupportDto dto) {
        return ChatSupport.builder()
            .id(dto.getId())
            .customerId(dto.getCustomerId())
            .agentId(dto.getAgentId())
            .status(ChatSupportStatus.valueOf(dto.getStatus()))
            .subject(dto.getSubject())
            .messages(dto.getMessages())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .closedAt(dto.getClosedAt())
            .isDeleted(dto.isDeleted())
            .deletedAt(dto.getDeletedAt())
            .build();
    }

    public static List<ChatSupportDto> toDtoList(List<ChatSupport> list) {
        if (list == null || list.isEmpty()) {
            return List.of();
        }

        return list.stream()
            .map(ChatSupportDto::toDto)
            .collect(Collectors.toList());
    }
}
