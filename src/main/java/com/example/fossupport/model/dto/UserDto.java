package com.example.fossupport.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true)
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String role;
}
