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

    // 현재 로그인한 사람의 아이디 관련 정보를 가져오는 함수
    private Auth.IdInterface getIdInterface() {
        Auth.IdInterface idInterface = (Auth.IdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idInterface;
    }
    
    // 식당 이름을 검색하지 않았을 때 오류가 발생하지 않도록 null을 ""로 바꿔주는 함수
    private String getKeyword(String keyword) {
        return keyword == null ? "" : keyword;
    }

    // 이름순서대로 식당들 목록을 반환하는 함수
    // 원하는 페이지의 범위만큼, 그리고 식당 이름을 검색했다면 그 이름으로 시작하는 식당들만 반환한다.
    public List<RestaurantInformationInterface> getOrderByName(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByName(pageable, getKeyword(keyword)).getContent();
        return result;
    }

    // 별점이 높은 순서대로 식당들 목록을 반환하는 함수
    // 원하는 페이지의 범위만큼, 그리고 식당 이름을 검색했다면 그 이름으로 시작하는 식당들만 반환한다.
    public List<RestaurantInformationInterface> getOrderByReviewScore(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByReviewScore(pageable, getKeyword(keyword)).getContent();
        return result;
    }

    // 거리가 가까운 순서대로 식당들 목록을 반환하는 함수. 이 때 거리는 위도, 경도를 기반으로 계산한다.
    // 원하는 페이지의 범위만큼, 그리고 식당 이름을 검색했다면 그 이름으로 시작하는 식당들만 반환한다.
    public List<RestaurantInformationInterface> getOrderByLength(Pageable pageable, double lat, double lnt, String keyword) {
        var result = this.restaurantRepository.findAllOrderByLength(pageable, getKeyword(keyword), lat, lnt).getContent();
        return result;
    }

    // 이름순서대로 현재 로그인한 식당 주인의 식당들 목록을 반환하는 함수
    // 원하는 페이지의 범위만큼, 그리고 식당 이름을 검색했다면 그 이름으로 시작하는 식당들만 반환한다.
    public List<RestaurantInformationInterface> getOrderByNameForOwner(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByNameForOwner(pageable, getKeyword(keyword), getIdInterface().getId()).getContent();
        return result;
    }

    // 별점이 높은 순서대로 현재 로그인한 식당 주인의 식당들 목록을 반환하는 함수
    // 원하는 페이지의 범위만큼, 그리고 식당 이름을 검색했다면 그 이름으로 시작하는 식당들만 반환한다.
    public List<RestaurantInformationInterface> getOrderByReviewScoreForOwner(Pageable pageable, String keyword) {
        var result = this.restaurantRepository.findAllOrderByReviewScoreForOwner(pageable, getKeyword(keyword), getIdInterface().getId()).getContent();
        return result;
    }

    // 거리가 가까운 순서대로 현재 로그인한 식당 주인의 식당들 목록을 반환하는 함수. 이 때 거리는 위도, 경도를 기반으로 계산한다.
    // 원하는 페이지의 범위만큼, 그리고 식당 이름을 검색했다면 그 이름으로 시작하는 식당들만 반환한다.
    public List<RestaurantInformationInterface> getOrderByLengthForOwner(Pageable pageable, double lat, double lnt, String keyword) {
        var result = this.restaurantRepository.findAllOrderByLengthForOwner(pageable, getKeyword(keyword), getIdInterface().getId(), lat, lnt).getContent();
        return result;
    }

    // 식당 아이디를 기반으로 식당 정보를 반환하는 함수
    public RestaurantInformationInterface getFromId(int id) {
        // 리뷰점수 평균값이 null이면 0을 반환하도록 설정되어 있어서 작동되지 않기 때문에 밑에서 따로 null값 조사하도록 구현
        RestaurantInformationInterface restaurant = this.restaurantRepository.getRestaurantFromId(id)
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않습니다."));

        if(restaurant.getId() == null) {
            throw new RuntimeException("현재 식당 정보가 존재하지 않습니다.");
        }
        return restaurant;
    }

    // 식당을 새로 등록할 때 사용하는 함수
    public Restaurant register(ForRegisterRestaurant forRegisterRestaurant) {
        var result = this.restaurantRepository.save(forRegisterRestaurant.toEntity(getIdInterface().getId()));
        return result;
    }

    // 기존의 식당을 새로 수정할 때 사용하는 함수, 식당 주인만 수정이 가능하다.
    public Restaurant update(ForRequestRestaurant forRequestRestaurant) {
        Restaurant restaurant = this.restaurantRepository.findAllByIdAndOwnerid(forRequestRestaurant.getRestaurantid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않거나 로그인한 계정에서 접근할 수 있는 권한이 없습니다."));

        restaurant.updateRestaurant(forRequestRestaurant);
        var result = this.restaurantRepository.save(restaurant);
        return result;
    }

    // 기존의 식당을 제거할 때 사용하는 함수, 식당 주인만 제거가 가능하다.
    public String delete(ForRequestRestaurant forRequestRestaurant) {
        Restaurant restaurant = this.restaurantRepository.findAllByIdAndOwnerid(forRequestRestaurant.getRestaurantid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 식당 정보가 존재하지 않거나 로그인한 계정에서 접근할 수 있는 권한이 없습니다."));

        this.restaurantRepository.delete(restaurant);
        return "Delete Complete " + restaurant.getId() + " : " + restaurant.getName();
    }


}
