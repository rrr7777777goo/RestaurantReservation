package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Member;
import com.restaurantreservation.domain.MemberIdInterface;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findBySignupid(String signupid);

    @Query(value="select m.id as id from com.restaurantreservation.domain.Member as m where m.signupid = ?1")
    Optional<MemberIdInterface> findidBySignupid(String signupid);

    boolean existsBySignupid(String signupid);
}
