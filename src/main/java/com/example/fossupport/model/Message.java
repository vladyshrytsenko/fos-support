package com.example.fossupport.model;

import com.example.fossupport.model.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String chatId;
    private long senderId;
    private String senderName;
    private String senderRole;
    private String content;
    private MessageStatus status;
    private List<String> attachments = new ArrayList<>();
    private LocalDateTime createdAt;
}
