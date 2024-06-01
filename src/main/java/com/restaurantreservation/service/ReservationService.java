package com.restaurantreservation.service;

import com.restaurantreservation.domain.member.Auth;
import com.restaurantreservation.domain.reservation.*;
import com.restaurantreservation.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final String startTimeString = "00:00:00";
    private final String endTimeString = "23:59:59";

    private Auth.IdInterface getIdInterface() {
        Auth.IdInterface idInterface = (Auth.IdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idInterface;
    }

    public Reservation register(ForRegisterReservation forRegisterReservation) {
        var result = this.reservationRepository.save(forRegisterReservation.toEntity(getIdInterface().getId()));

        return result;
    }

    public Reservation approve_or_reject(ForRequestReservation forRequestReservation, ApproveStatus approveStatus) {
        Reservation reservation = this.reservationRepository.findReservationForApproveOrDeny(forRequestReservation.getReservationid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 예약정보를 승인할 수 있는 권한이 없습니다."));

        switch(approveStatus) {
            case RESERVATION_APPROVE:
                reservation.approveReservation();
                break;
            case RESERVATION_REJECT:
                reservation.rejectReservation();
                break;
        }

        var result = this.reservationRepository.save(reservation);

        return result;
    }

    public LocalDateTime parseLocalDateTime(String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        StringBuilder sb = new StringBuilder(date.substring(0, 10));
        sb.append('T');
        sb.append(time);

        return LocalDateTime.parse(sb.toString(), formatter);
    }

    public List<ReservationInformationInterface> getReservationForDateAndLoginOwner(Pageable pageable, String date) {
        LocalDateTime startTime = parseLocalDateTime(date, startTimeString);
        LocalDateTime endTime = parseLocalDateTime(date, endTimeString);

        var result = this.reservationRepository.findReservationForReservationDateAndOwnerId(pageable, startTime, endTime, getIdInterface().getId()).getContent();

        return result;
    }

    public List<ReservationInformationInterface> getReservationForDateAndLoginUser(Pageable pageable, String date) {
        LocalDateTime startTime = parseLocalDateTime(date, startTimeString);
        LocalDateTime endTime = parseLocalDateTime(date, endTimeString);

        var result = this.reservationRepository.findReservationForReservationDateAndUserId(pageable, startTime, endTime, getIdInterface().getId()).getContent();

        return result;
    }

    public Reservation visit(ForRequestReservation forRequestReservation) {
        Reservation result = this.reservationRepository.findAllByIdAndUserid(forRequestReservation.getReservationid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 예약정보를 승인할 수 있는 권한이 없습니다."));
        result.visitReservation();
        this.reservationRepository.save(result);
        return result;
    }

    public ReservationInformationInterface getFromId(int id) {
        var result = this.reservationRepository.getReservationFromIdAndUserId(id, getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 예약정보를 확인할 수 있는 권한이 없습니다."));
        return result;
    }
}
