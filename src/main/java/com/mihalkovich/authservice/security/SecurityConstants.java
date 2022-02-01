package com.mihalkovich.authservice.security;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SecurityConstants {

    public static String SECRET = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

}
