package com.restaurantreservation.controller;

import com.restaurantreservation.domain.reservation.ApproveStatus;
import com.restaurantreservation.domain.reservation.ForRequestReservation;
import com.restaurantreservation.domain.reservation.ForRegisterReservation;
import com.restaurantreservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    // 식당 주인이 특정 날짜에 자신에게 들어온 예약 정보들을 얻기 위해 사용
    @GetMapping("/get/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?>  getReservationForOwner(Pageable pageable, @RequestParam String date) {
        var result = this.reservationService.getReservationForDateAndLoginOwner(pageable, date);
        return ResponseEntity.ok(result);
    }

    // 사용자가 특정 날짜에 자신이 신청한 예약 정보들을 얻기 위해 사용
    @GetMapping("/get/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?>  getReservationForUser(Pageable pageable, @RequestParam String date) {
        var result = this.reservationService.getReservationForDateAndLoginUser(pageable, date);
        return ResponseEntity.ok(result);
    }

    // 아이디를 기반으로 예약 정보를 얻기 위해 사용
    @GetMapping("/get/fromid")
    @PreAuthorize("hasRole('USER') or hasRole('OWNER')")
    public ResponseEntity<?> getReservationFromId(@RequestParam int id) {
        var result = this.reservationService.getFromId(id);
        return ResponseEntity.ok(result);
    }

    // 새로 예약 정보를 등록할 때 사용
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addReservation(@RequestBody ForRegisterReservation request) {
        var result = this.reservationService.register(request);
        return ResponseEntity.ok(result);
    }

    // 예약 정보를 승인할 때 사용
    @PutMapping("/approve")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> reservationApprove(@RequestBody ForRequestReservation request) {
        var result = this.reservationService.approve_or_reject(request, ApproveStatus.RESERVATION_APPROVE);
        return ResponseEntity.ok(result);
    }

    // 예약 정보를 거부할 때 사용
    @PutMapping("/reject")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> reservationDeny(@RequestBody ForRequestReservation request) {
        var result = this.reservationService.approve_or_reject(request, ApproveStatus.RESERVATION_REJECT);
        return ResponseEntity.ok(result);
    }

    // 예약 방문 처리를 할 때 사용
    @PutMapping("/visit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> reservationVisit(@RequestBody ForRequestReservation request) {
        var result = this.reservationService.visit(request);
        return ResponseEntity.ok(result);
    }
}
