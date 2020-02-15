package com.cqust.jwt;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {

    // √‹‘ø
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    public Object getPrincipal() {
        return token;
    }

    public Object getCredentials() {
        return token;
    }
}
