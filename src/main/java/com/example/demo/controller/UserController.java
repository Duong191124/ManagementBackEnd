package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.models.Users;
import com.example.demo.response.AccessTokenResponse;
import com.example.demo.response.LoginResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.service.Impl.TokenServiceImpl;
import com.example.demo.service.Impl.UserServiceImpl;
import com.example.demo.util.SecurityUtil;
import com.example.demo.util.UserDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private TokenServiceImpl tokenService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> getAll(){
        List<UserResponse> userList = userService.getAll()
                .stream()
                .map(UserResponse::fromUserResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .message("Lay thong tin thanh cong")
                .status(HttpStatus.OK.value())
                .data(userList)
                .build());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable("id") int id){
        Users existingUser = userService.getById(id);
        return ResponseEntity.ok(UserResponse.fromUserResponse(existingUser));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                .message("Sua thong tin thanh cong")
                .status(HttpStatus.OK.value())
                .data(userService.update(id, userDTO))
                .build());
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        userService.delete(id);
        return ResponseEntity.ok().body("Delete user with id = "+ id + " successfully!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO){
        try {
            Users users = userService.save(userDTO);
            UserResponse userResponse = UserResponse.fromUserResponse(users);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(MessageResponse.builder()
                            .message("Them moi thanh cong")
                            .status(HttpStatus.CREATED.value())
                            .data(userResponse)
                            .build());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserDTO userDTO){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDTO.getUsername(), userDTO.getPassword()
        );
    
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    
        String access_token = securityUtil.createToken(authentication, false);

        tokenService.blackListAccessToken(access_token, 10, TimeUnit.MINUTES);

        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("USER");

        ResponseCookie cookie = ResponseCookie.from("refresh_token", access_token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
    
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Login Successfully")
                    .token(access_token)
                    .role(role)
                    .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken
            (@CookieValue(value = "refresh_token", defaultValue = "") String refreshToken,
             @RequestHeader(value = "Authorization", required = false) String authorizationHeader)
    {
        // Lấy access token từ Authorization header
        String oldAccessToken = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            oldAccessToken = authorizationHeader.substring(7);
        }

        if(securityUtil.validateToken(refreshToken)){
            String username = jwtDecoder.decode(refreshToken).getSubject();
            Collection<? extends GrantedAuthority> authorities = getAuthoritiesForUser(username);

            // Kiểm tra access_token cũ trong blacklist
            if (oldAccessToken != null && tokenService.isTokenBlacklisted(oldAccessToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Access token has been blacklisted. Please login again.");
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            String newAccessToken = securityUtil.createToken(authentication, false);
            String newRefreshToken = securityUtil.createToken(authentication, true);

            ResponseCookie cookie = ResponseCookie.from("refresh_token", newRefreshToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60)
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(AccessTokenResponse.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build());
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired. Please login again.");
        }
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesForUser(String username) {
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        return userDetails.getAuthorities(); // Trả về danh sách quyền hạn
    }

}
