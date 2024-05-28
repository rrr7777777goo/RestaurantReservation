package com.restaurantreservation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity(name = "restaurant")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    // 고유번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 점장 아이디
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
}
