package com.restaurantreservation.service;

import com.restaurantreservation.domain.Auth;
import com.restaurantreservation.domain.Member;
import com.restaurantreservation.domain.MemberIdInterface;
import com.restaurantreservation.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService {
    private PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    private final int minLength_signupid = 6;
    private final int maxLength_signupid = 20;

    private final int minLength_password = 8;
    private final int maxLength_password = 20;

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

    public boolean isSignupidValid(String signupid) {
        return this.memberRepository.existsBySignupid(signupid);
    }

    public Member authenticate(Auth.SignIn member) {
        var user = this.memberRepository.findBySignupid(member.getSignupid())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다. -> " + member.getSignupid()));

        if(!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    public MemberIdInterface xxx() {
        var x = this.memberRepository.findidBySignupid("grace2").orElseThrow(() -> new UsernameNotFoundException("!!"));

        return x;
    }
    public void yopyop() {
        // Member signinMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 로그인 ID 추출

        Collection<? extends GrantedAuthority> xx = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for(GrantedAuthority z : xx) {
            System.out.println(z);
            System.out.println(z.getAuthority());
        }
        System.out.println(username);
    }
}
