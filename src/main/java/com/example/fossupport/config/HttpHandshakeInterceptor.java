package com.example.fossupport.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Map<String, Object> attributes) {

        if (request instanceof ServletServerHttpRequest serverRequest) {
            String token = serverRequest.getServletRequest().getParameter("token"); // ?token=...
            if (token != null && token.startsWith("Bearer ")) {
                try {
                    Jwt jwt = this.jwtDecoder.decode(token.substring(7));
                    attributes.put("user", jwt.getClaimAsString("sub"));
                } catch (JwtException e) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Exception exception) {

    }

    private final JwtDecoder jwtDecoder;
}
