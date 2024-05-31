package com.restaurantreservation.service;

import com.restaurantreservation.domain.restaurant.ForRegisterRestaurant;
import com.restaurantreservation.domain.restaurant.Restaurant;
import com.restaurantreservation.domain.restaurant.RestaurantInformationInterface;
import com.restaurantreservation.domain.member.MemberIdInterface;
import com.restaurantreservation.repository.MemberRepository;
import com.restaurantreservation.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    private MemberIdInterface getMemberIdInterface() {
        MemberIdInterface memberIdInterface = (MemberIdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인중인 ID
        return memberIdInterface;
    }

    private String getKeyword(String keyword) {
        return keyword == null ? "" : keyword;
    }

    public Restaurant register(ForRegisterRestaurant forRegisterRestaurant) {
        var result = this.restaurantRepository.save(forRegisterRestaurant.toEntity(getMemberIdInterface().getId()));
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByName(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByName(pageable, getKeyword(keyword)).getContent();
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByReviewScore(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByReviewScore(pageable, getKeyword(keyword)).getContent();
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByLength(Pageable pageable, double lat, double lnt, String keyword) {
        var result = this.restaurantRepository.findAllOrderByLength(pageable, getKeyword(keyword), lat, lnt).getContent();
        return result;
    }
}
