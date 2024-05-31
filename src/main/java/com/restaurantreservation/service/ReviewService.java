package com.restaurantreservation.service;

import com.restaurantreservation.domain.member.MemberIdInterface;
import com.restaurantreservation.domain.reservation.Reservation;
import com.restaurantreservation.domain.review.ForRegisterReview;
import com.restaurantreservation.domain.review.ForRequestReview;
import com.restaurantreservation.domain.review.Review;
import com.restaurantreservation.repository.MemberRepository;
import com.restaurantreservation.repository.ReservationRepository;
import com.restaurantreservation.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    private MemberIdInterface getMemberIdInterface() {
        MemberIdInterface memberIdInterface = (MemberIdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인중인 ID
        return memberIdInterface;
    }

    public Review register(ForRegisterReview forRegisterReview) {
        // 이미 리뷰가 작성되어 있는지 확인하기 위한 코드
        boolean exists = this.reviewRepository.existsByReservationid(forRegisterReview.getReservationid());
        if(exists) {
            throw new RuntimeException("현재 예약 정보는 이미 리뷰가 작성된 상태입니다.");
        }

        // 현재 계정에서 현재 예약정보에 대한 리뷰를 작성할 수 있는지 확인하기 위한 코드
        Reservation reservation = this.reservationRepository.findAllByIdAndUserid(forRegisterReview.getReservationid(), getMemberIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 해당 예약정보에 대해 리뷰를 작성할 수 있는 권한이 없습니다."));
        reservation.checkBeforeReview();

        var result = this.reviewRepository.save(forRegisterReview.toEntity());
        return result;
    }

    public Review update(ForRequestReview forRequestReview) {
        // 현재 계정에서 현재 예약정보에 대해 접근할 수 있는지 확인하기 위한 코드
        this.reservationRepository.existsByReviewIdAndUserid(forRequestReview.getReviewid(), getMemberIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 리뷰에 접근할 수 있는 권한이 없습니다."));

        // 아이디 기반 호출
        Review review = this.reviewRepository.findAllById(forRequestReview.getReviewid())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않습니다."));

        review.updateReview(forRequestReview);
        var result = this.reviewRepository.save(review);
        return result;
    }
}
