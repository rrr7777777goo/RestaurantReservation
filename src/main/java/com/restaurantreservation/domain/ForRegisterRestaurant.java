package com.restaurantreservation.domain;

import lombok.Data;

@Data
public class ForRegisterRestaurant {
    private String name;
    private String address;
    private String description;
    private double lat;
    private double lnt;

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
