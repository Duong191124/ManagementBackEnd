package com.example.demo.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CustomerGrandAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        // Lấy quyền từ claim "roles"
        List<String> roles = (List<String>) claims.get("roles");

        if (roles == null) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu không có roles
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new) // Chuyển đổi thành GrantedAuthority
                .collect(Collectors.toList());
    }
}
