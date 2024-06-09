package com.restaurantreservation.domain.restaurant;

import lombok.Data;

// 식당 관련 정보를 수정하거나 식당을 삭제할 때 사용하는 클래스 (삭제할 때는 restaurantid만 있어도 된다.)
@Data
public class ForRequestRestaurant {
    private int restaurantid; // 식당 아이디
    private String name; // 식당 이름
    private String address; // 식당 주소
    private String description; // 식당 설명
    private double lat; // 식당 위도
    private double lnt; // 식당 경도
}
