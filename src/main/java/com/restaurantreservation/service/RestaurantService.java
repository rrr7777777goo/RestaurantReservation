package com.restaurantreservation.service;

import com.restaurantreservation.domain.member.Auth;
import com.restaurantreservation.domain.restaurant.ForRegisterRestaurant;
import com.restaurantreservation.domain.restaurant.ForRequestRestaurant;
import com.restaurantreservation.domain.restaurant.Restaurant;
import com.restaurantreservation.domain.restaurant.RestaurantInformationInterface;
import com.restaurantreservation.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    private Auth.IdInterface getIdInterface() {
        Auth.IdInterface idInterface = (Auth.IdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idInterface;
    }

    private String getKeyword(String keyword) {
        return keyword == null ? "" : keyword;
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

    public List<RestaurantInformationInterface> getOrderByNameForOwner(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByNameForOwner(pageable, getKeyword(keyword), getIdInterface().getId()).getContent();
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByReviewScoreForOwner(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByReviewScoreForOwner(pageable, getKeyword(keyword), getIdInterface().getId()).getContent();
        return result;
    }

    public List<RestaurantInformationInterface> getOrderByLengthForOwner(Pageable pageable, double lat, double lnt, String keyword) {
        var result = this.restaurantRepository.findAllOrderByLengthForOwner(pageable, getKeyword(keyword), getIdInterface().getId(), lat, lnt).getContent();
        return result;
    }

    public RestaurantInformationInterface getFromId(int id) {
        var restaurant = this.restaurantRepository.getRestaurantFromId(id)
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않습니다."));
        return restaurant;
    }

    public Restaurant register(ForRegisterRestaurant forRegisterRestaurant) {
        var result = this.restaurantRepository.save(forRegisterRestaurant.toEntity(getIdInterface().getId()));
        return result;
    }

    public Restaurant update(ForRequestRestaurant forRequestRestaurant) {
        this.restaurantRepository.existsByIdAndOwnerid(forRequestRestaurant.getRestaurantid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않거나 로그인한 계정에서 접근할 수 있는 권한이 없습니다."));

        Restaurant restaurant = this.restaurantRepository.findAllById(forRequestRestaurant.getRestaurantid())
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않습니다."));

        restaurant.updateRestaurant(forRequestRestaurant);
        var result = this.restaurantRepository.save(restaurant);
        return result;
    }

    public String delete(ForRequestRestaurant forRequestRestaurant) {
        this.restaurantRepository.existsByIdAndOwnerid(forRequestRestaurant.getRestaurantid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않거나 로그인한 계정에서 접근할 수 있는 권한이 없습니다."));

        var restaurant = this.restaurantRepository.findAllById(forRequestRestaurant.getRestaurantid())
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않습니다."));

        this.restaurantRepository.delete(restaurant);
        return "Delete Complete " + restaurant.getId() + " : " + restaurant.getName();
    }


}
