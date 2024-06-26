package com.restaurantreservation.service;

import com.restaurantreservation.domain.member.Auth;
import com.restaurantreservation.domain.member.Member;
import com.restaurantreservation.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final int minLength_signupid = 6; // 아이디 최소 길이
    private final int maxLength_signupid = 20; // 아이디 최대 길이

    private final int minLength_password = 8; // 비밀번호 최소 길이
    private final int maxLength_password = 20; // 비밀번호 최대 길이

    // 회원가입
    public Member register(Auth.SignUp member) {
        if(member.getSignupid().length() < minLength_signupid || member.getSignupid().length() > maxLength_signupid) {
            throw new RuntimeException("아이디의 길이는 " + minLength_signupid + "이상 " + maxLength_signupid + "이하여야 합니다.");
        }
        if(member.getPassword().length() < minLength_password || member.getPassword().length() > maxLength_password) {
            throw new RuntimeException("패스워드의 길이는 " + minLength_password + "이상 " + maxLength_password + "이하여야 합니다.");
        }
        boolean exists = this.memberRepository.existsBySignupid(member.getSignupid());
        if(exists) {
            throw new RuntimeException("이미 존재하는 ID입니다. -> " + member.getSignupid());
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.memberRepository.save(member.toEntity());
        return result;
    }

    // 아이디를 기반으로 유저 정보 가져오기
    public Auth.IdInterface loadUserBySignupid(String signupid) {
        return this.memberRepository.findidBySignupid(signupid)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다. -> " + signupid));
    }

    // 입력받은 아이디, 비밀번호를 기반으로 유저정보 가져오기 (로그인)
    public Member authenticate(Auth.SignIn member) {
        var user = this.memberRepository.findBySignupid(member.getSignupid())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다. -> " + member.getSignupid()));

        if(!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
