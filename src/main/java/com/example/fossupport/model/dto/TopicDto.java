package com.example.fossupport.model.dto;

import com.example.fossupport.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TopicDto {

    private String subject;
    private Message message;
}
