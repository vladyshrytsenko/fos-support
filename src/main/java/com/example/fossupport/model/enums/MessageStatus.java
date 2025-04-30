package com.example.fossupport.model.enums;

public enum MessageStatus {
    RECEIVED,
    DELIVERED,
    READ;

    public static MessageStatus of(String value) {
        return MessageStatus.valueOf(value);
    }
}
