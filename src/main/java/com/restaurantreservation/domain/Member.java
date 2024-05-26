package com.restaurantreservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Entity(name = "user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 아이디 (6 ~ 20자)
    private String signupid;

    // 비밀번호(8 ~ 20자)
    @JsonIgnore
    private String password;

    // 역할 저장용 (50자 이내)
    // 사용자 역할 (ROLE_USER)
    // 점장 역할 (ROLE_OWNER)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name= "user_id", referencedColumnName = "id"))
    @Column(name = "role")
    private Set<String> roles;
}
