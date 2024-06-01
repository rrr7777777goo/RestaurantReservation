package com.restaurantreservation.domain.restaurant;

public interface RestaurantInformationInterface {
    // 식당 아이디
    Integer getId();
    // 식당 이름
    String getName();
    // 식당 주소
    String getAddress();

    // 식당 설명
    String getDescription();

    // 식당 위도
    Double getLat();

    // 식당 경도
    Double getLnt();

    // 식당 리뷰 별점
    Double getReviewscore();
}
