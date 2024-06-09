package com.restaurantreservation.domain.member;

import lombok.Data;

import java.util.Set;

public class Auth {
    // 로그인용 클래스
    @Data
    public static class SignIn {
        private String signupid; // 아이디
        private String password; // 비밀번호
    }
    
    // 회원가입용 클래스
    @Data
    public static class SignUp {
        private String signupid; // 생성할 계정의 아이디
        private String password; // 생성할 계정의 비밀번호
        private Set<String> roles; // 생성할 계정의 역할목록 

        // 현재 가지고 있는 값들을 기반으로 Member 객체 반환
        public Member toEntity() {
            return Member.builder()
                    .signupid(this.signupid)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }
    }
    
    // ID관련 정보 저장용 인터페이스
    public interface IdInterface {
        int getId(); // 고유번호
        String getSignupid(); // 아이디
    }
}
