package com.restaurantreservation.service;

import com.restaurantreservation.domain.*;
import com.restaurantreservation.repository.MemberRepository;
import com.restaurantreservation.repository.ReservationRepository;
import com.restaurantreservation.repository.RestaurantRepository;
import com.restaurantreservation.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<RestaurantInformationInterface> getOrderByName(Pageable pageable) {
        var result = this.restaurantRepository.findAllOrderByName(pageable).getContent();
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByReviewScore(Pageable pageable) {
        var result = this.restaurantRepository.findAllOrderByReviewScore(pageable).getContent();
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByLength(Pageable pageable, double lat, double lnt) {
        var result = this.restaurantRepository.findAllOrderByLength(pageable, lat, lnt).getContent();
        return result;
    }
}
