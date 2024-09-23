package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityUtil {

    @Value("${secret.key}")
    private String secretKey;

    @Value("${duration.date}")
    private Long duration;

    @Value("${refresh-token}")
    private Long refresh;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    //Khai báo thuật toán để hashcode secretkey
    public final MacAlgorithm macAlgorithm = MacAlgorithm.HS256;

    public String createToken(Authentication authentication, boolean isRefreshToken){
        //build header
        JwsHeader jwsHeader = JwsHeader.with(macAlgorithm).build();

        //build payload
        //set time out of date
        Instant now = Instant.now();
        Instant validity;

        if(isRefreshToken){
            validity = now.plus(this.refresh, ChronoUnit.SECONDS);
        }else{
            validity = now.plus(this.duration, ChronoUnit.SECONDS);
        }

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now) //time start
                .expiresAt(validity)//time end
                .subject(authentication.getName())
                .claim("type", isRefreshToken ? "refresh" : "access")
                .claim("roles", roles)
                .build();

        //return to token and build signature from header and payload
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)).getTokenValue();
    }


    public boolean validateToken(String token){
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getExpiresAt().isAfter(Instant.now());
        }catch (Exception e){
            return false;
        }
    }

}
