package com.restaurantreservation.repository;

import com.restaurantreservation.domain.member.Auth;
import com.restaurantreservation.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findBySignupid(String signupid);

    @Query(value="select m.id as id, m.signupid as signupid \n" +
            " from com.restaurantreservation.domain.member.Member as m \n" +
            " where m.signupid = ?1")
    Optional<Auth.IdInterface> findidBySignupid(String signupid);

    boolean existsBySignupid(String signupid);
}
