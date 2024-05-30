package com.restaurantreservation.repository;

import com.restaurantreservation.domain.restaurant.Restaurant;
import com.restaurantreservation.domain.restaurant.RestaurantInformationInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
            "where upper(a.name) like upper(concat(?1, '%')) \n" +
            "group by a.id\n" +
            "order by a.name asc\n")
    Page<RestaurantInformationInterface> findAllOrderByName(Pageable pageable, String keyword);

    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
            "where upper(a.name) like upper(concat(?1, '%')) \n" +
            "group by a.id\n" +
            "order by reviewscore desc\n")
    Page<RestaurantInformationInterface> findAllOrderByReviewScore(Pageable pageable, String keyword);

    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
            "where upper(a.name) like upper(concat(?1, '%')) \n" +
            "group by a.id\n" +
            "order by power(lat - ?2, 2) + power(lnt - ?3, 2) asc\n")
    Page<RestaurantInformationInterface> findAllOrderByLength(Pageable pageable, String keyword, double lat, double lnt);

    boolean existsById(int id);
}
