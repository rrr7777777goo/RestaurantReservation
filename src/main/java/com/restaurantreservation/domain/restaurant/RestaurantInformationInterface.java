package com.restaurantreservation.domain.restaurant;

public interface RestaurantInformationInterface {
    // 식당 아이디
    int getId();

    // 식당 이름
    String getName();
    // 식당 주소
    String getAddress();

    // 식당 설명
    String getDescription();

    // 식당 위도
    double getLat();

    // 식당 경도
    double getLnt();

    // 식당 리뷰 별점
    double getReviewscore();
}
