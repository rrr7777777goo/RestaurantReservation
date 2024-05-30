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
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addReservation(@RequestBody ForRegisterReservation request) {
        var result = this.reservationService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> reservationApprove(@RequestBody ForRequestReservation request) {
        var result = this.reservationService.approve_or_reject(request, ApproveStatus.RESERVATION_APPROVE);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?> reservationDeny(@RequestBody ForRequestReservation request) {
        var result = this.reservationService.approve_or_reject(request, ApproveStatus.RESERVATION_REJECT);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/visit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> reservationVisit(@RequestBody ForRequestReservation request) {
        var result = this.reservationService.visit(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getForDate")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<?>  getReservationForDate(Pageable pageable, @RequestParam String date) {
        var result = this.reservationService.getReservationForDateAndLoginOwner(pageable, date);
        return ResponseEntity.ok(result);
    }
}
