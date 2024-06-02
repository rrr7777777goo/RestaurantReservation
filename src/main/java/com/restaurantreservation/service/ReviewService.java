package com.restaurantreservation.service;

import com.restaurantreservation.domain.member.Auth;
import com.restaurantreservation.domain.reservation.Reservation;
import com.restaurantreservation.domain.review.ForRegisterReview;
import com.restaurantreservation.domain.review.ForRequestReview;
import com.restaurantreservation.domain.review.Review;
import com.restaurantreservation.domain.review.ReviewInformationInterface;
import com.restaurantreservation.repository.ReservationRepository;
import com.restaurantreservation.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    private Auth.IdInterface getIdInterface() {
        Auth.IdInterface idInterface = (Auth.IdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idInterface;
    }

    public List<ReviewInformationInterface> getOrderByTime(Pageable pageable, int restaurantid) {
        var result = this.reviewRepository.findAllByRestaurantIdOrderByTime(pageable, restaurantid).getContent();
        return result;
    }

    public ReviewInformationInterface getFromid(int id) {
        System.out.println("와우");
        var result = this.reviewRepository.getReviewFromIdAndUserId(id, getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 리뷰 정보가 존재하지 않거나 로그인한 계정에서 리뷰정보를 확인할 수 있는 권한이 없습니다."));
        return result;
    }

    public Review register(ForRegisterReview forRegisterReview) {
        // 이미 리뷰가 작성되어 있는지 확인하기 위한 코드
        boolean exists = this.reviewRepository.existsByReservationid(forRegisterReview.getReservationid());
        if(exists) {
            throw new RuntimeException("현재 예약 정보는 이미 리뷰가 작성된 상태입니다.");
        }

        // 현재 계정에서 현재 예약정보에 대한 리뷰를 작성할 수 있는지 확인하기 위한 코드
        Reservation reservation = this.reservationRepository.findAllByIdAndUserid(forRegisterReview.getReservationid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 해당 예약정보에 대해 리뷰를 작성할 수 있는 권한이 없습니다."));
        reservation.checkBeforeReview();

        var result = this.reviewRepository.save(forRegisterReview.toEntity());
        return result;
    }

    public Review update(ForRequestReview forRequestReview) {
        // 현재 계정에서 현재 예약정보에 대해 접근할 수 있는지 확인하기 위한 코드
        Review review = this.reviewRepository.getReviewForUpdateFromIdAndUserId(forRequestReview.getReviewid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 리뷰 정보가 존재하지 않거나 로그인한 계정에서 접근할 수 있는 권한이 없습니다."));

        review.updateReview(forRequestReview);
        var result = this.reviewRepository.save(review);
        return result;
    }

    public String delete(ForRequestReview forRequestReview) {
        var review = this.reviewRepository.getReviewForDeleteFromIdAndUserId(forRequestReview.getReviewid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 리뷰 정보가 존재하지 않거나 로그인한 계정에서 접근할 수 있는 권한이 없습니다."));

        this.reviewRepository.delete(review);
        return "Delete Complete " + review.getId();
    }
}
