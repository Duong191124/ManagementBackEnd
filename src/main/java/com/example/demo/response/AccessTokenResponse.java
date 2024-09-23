package com.example.demo.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponse {
    private String accessToken;
    private String refreshToken;
}
