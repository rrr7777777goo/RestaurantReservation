package com.restaurantreservation.domain.restaurant;

import lombok.Data;

// 식당을 새로 생성할 때 사용하는 클래스
@Data
public class ForRegisterRestaurant {
    private String name; // 식당 이름
    private String address; // 식당 주소
    private String description; // 식당 설명
    private double lat; // 식당 위도
    private double lnt; // 식당 경도

    // 식당 객체 반환 (매개변수로 현재 식당 주인의 아이디를 입력받는다.)
    public Restaurant toEntity(int ownerid) {
        return Restaurant.builder()
                .ownerid(ownerid)
                .name(this.name)
                .address(this.address)
                .description(this.description)
                .lat(this.lat)
                .lnt(this.lnt)
                .build();
    }
}
