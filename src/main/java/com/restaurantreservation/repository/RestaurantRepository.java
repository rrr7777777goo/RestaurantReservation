package com.restaurantreservation.repository;

import com.restaurantreservation.domain.restaurant.Restaurant;
import com.restaurantreservation.domain.restaurant.RestaurantInformationInterface;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    // 이름 순서대로 식당 정보들을 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
            "where upper(a.name) like upper(concat(?1, '%')) \n" +
            "group by a.id\n" +
            "order by a.name asc, a.id asc\n")
    Page<RestaurantInformationInterface> findAllOrderByName(Pageable pageable, String keyword);

    // 이름 순서대로 현재 식당 주인의 식당 정보들을 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
                    "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
                    "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
                    "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
                    "where upper(a.name) like upper(concat(?1, '%')) and a.ownerid = ?2\n" +
                    "group by a.id\n" +
                    "order by a.name asc, a.id asc\n")
    Page<RestaurantInformationInterface> findAllOrderByNameForOwner(Pageable pageable, String keyword, int ownerid);

    // 별점이 높은 순서대로 식당 정보들을 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
            "where upper(a.name) like upper(concat(?1, '%')) \n" +
            "group by a.id\n" +
            "order by reviewscore desc, a.id asc\n")
    Page<RestaurantInformationInterface> findAllOrderByReviewScore(Pageable pageable, String keyword);

    // 별점이 높은 순서대로 현재 식당 주인의 식당 정보들을 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
                    "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
                    "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
                    "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
                    "where upper(a.name) like upper(concat(?1, '%')) and a.ownerid = ?2\n" +
                    "group by a.id\n" +
                    "order by reviewscore desc, a.id asc\n")
    Page<RestaurantInformationInterface> findAllOrderByReviewScoreForOwner(Pageable pageable, String keyword, int ownerid);

    // 거리가 가까운 순서대로 식당 정보들을 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
            "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
            "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
            "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
            "where upper(a.name) like upper(concat(?1, '%')) \n" +
            "group by a.id\n" +
            "order by power(lat - ?2, 2) + power(lnt - ?3, 2) asc, a.id asc\n")
    Page<RestaurantInformationInterface> findAllOrderByLength(Pageable pageable, String keyword, double lat, double lnt);

    // 거리가 가까운 순서대로 현재 식당 주인의 식당 정보들을 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore\n" +
                    "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
                    "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
                    "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
                    "where upper(a.name) like upper(concat(?1, '%')) and a.ownerid = ?2\n" +
                    "group by a.id\n" +
                    "order by power(lat - ?3, 2) + power(lnt - ?4, 2) asc, a.id asc\n")
    Page<RestaurantInformationInterface> findAllOrderByLengthForOwner(Pageable pageable, String keyword, int ownerid, double lat, double lnt);

    // 식당 아이디를 기반으로 필요한 정보들만 가져온다.
    @Query(value=
            "select a.id as id, a.name as name, a.address as address, a.description as description, a.lat as lat , a.lnt as lnt, (ifnull(avg(c.score), 0)) as reviewscore \n" +
                    "from com.restaurantreservation.domain.restaurant.Restaurant as a \n" +
                    "left join com.restaurantreservation.domain.reservation.Reservation as b on a.id = b.restaurantid \n" +
                    "left join com.restaurantreservation.domain.review.Review as c on b.id = c.reservationid\n" +
                    "where a.id = ?1")
    Optional<RestaurantInformationInterface> getRestaurantFromId(int id);

    // 식당 아이디, 식당 주인 아이디 두가지가 일치하는 식당을 가져온다.
    Optional<Restaurant> findAllByIdAndOwnerid(int id, int ownerid);

    // 아이디를 기반으로 식당 존재여부 확인
    boolean existsAllById(int id);
}
