package com.restaurantreservation.domain.member;

import lombok.Data;

import java.util.Set;

public class Auth {
    @Data
    public static class SignIn {
        private String signupid;
        private String password;
    }

    @Data
    public static class SignUp {
        private String signupid;
        private String password;
        private Set<String> roles;

        public Member toEntity() {
            return Member.builder()
                    .signupid(this.signupid)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }
    }
}
