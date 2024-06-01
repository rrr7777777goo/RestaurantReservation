package com.restaurantreservation.domain.restaurant;

import lombok.Data;

@Data
public class ForRequestRestaurant {
    private int restaurantid;
    private String name;
    private String address;
    private String description;
    private double lat;
    private double lnt;
}
