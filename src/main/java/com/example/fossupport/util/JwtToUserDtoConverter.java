//package com.example.fossupport.util;
//
//import com.example.fossupport.model.dto.UserDto;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import java.util.Collections;
//
//public class JwtToUserDtoConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//    @Override
//    public AbstractAuthenticationToken convert(Jwt jwt) {
//        Long id = jwt.getClaim("user_id") != null ? Long.valueOf(jwt.getClaimAsString("user_id")) : null;
//        String username = jwt.getClaimAsString("sub");
//        String email = jwt.getClaimAsString("email");
//        String role = jwt.getClaimAsString("role");
//
//        UserDto user = new UserDto(id, username, email, role);
//        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//    }
//}
