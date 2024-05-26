package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findBySignupid(String signupid);

    boolean existsBySignupid(String signupid);
}
