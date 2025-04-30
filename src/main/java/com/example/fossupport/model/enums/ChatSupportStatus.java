package com.example.fossupport.model.enums;

public enum ChatSupportStatus {
    OPEN,
    ASSIGNED,
    CLOSED;

    public static ChatSupportStatus of(String value) {
        return ChatSupportStatus.valueOf(value);
    }
}
