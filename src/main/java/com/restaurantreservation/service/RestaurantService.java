package com.restaurantreservation.service;

import com.restaurantreservation.domain.ForRegisterRestaurant;
import com.restaurantreservation.domain.MemberIdInterface;
import com.restaurantreservation.domain.Restaurant;
import com.restaurantreservation.repository.MemberRepository;
import com.restaurantreservation.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {
    private MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    public Restaurant register(ForRegisterRestaurant forRegisterRestaurant) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인중인 ID

        MemberIdInterface memberIdInterface = this.memberRepository.findidBySignupid(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 ID입니다. -> " + username));

        var result = this.restaurantRepository.save(forRegisterRestaurant.toEntity(memberIdInterface.getId()));

        return result;
    }
}
