package ru.atomskills.back.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
