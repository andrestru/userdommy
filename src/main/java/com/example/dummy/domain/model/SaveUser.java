package com.example.dummy.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUser {

    private String username;
    private Instant loginTime;
    private String accessToken;
    private String refreshToken;

}
