package com.restaurantreservation.controller;

import com.restaurantreservation.domain.member.Auth;
import com.restaurantreservation.security.TokenProvider;
import com.restaurantreservation.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    
    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request) {
        var result = this.memberService.register(request);
        return ResponseEntity.ok(result);
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request) {
        // 로그인용 API
        var member = this.memberService.authenticate(request);
        var token = this.tokenProvider.generateToken(member.getSignupid(), member.getRoles());
        log.info("user login -> " + request.getSignupid());

        return ResponseEntity.ok(token);
    }
}