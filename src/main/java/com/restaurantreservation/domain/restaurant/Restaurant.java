package com.restaurantreservation.domain.restaurant;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity(name = "restaurant")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 주인 아이디
    @Column(name = "owner_id")
    private int ownerid;

    // 식당 이름
    private String name;

    // 식당 주소
    private String address;

    // 식당 설명
    private String description;

    // 식당 위도
    private double lat;

    // 식당 경도
    private double lnt;

    // 식당 정보 수정용 함수
    public void updateRestaurant(ForRequestRestaurant forRequestRestaurant) {
        if(this.id == forRequestRestaurant.getRestaurantid()) {
            this.name = forRequestRestaurant.getName();
            this.address = forRequestRestaurant.getAddress();
            this.description = forRequestRestaurant.getDescription();
            this.lat = forRequestRestaurant.getLat();
            this.lnt = forRequestRestaurant.getLnt();
        }
    }
}
