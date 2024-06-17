package ru.atomskills.back.dto;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;
}
