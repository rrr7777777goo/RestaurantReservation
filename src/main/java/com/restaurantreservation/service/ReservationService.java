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

    private final String startTimeString = "00:00:00"; // 특정 날짜의 예약 정보들만 가져오기 위해서 사용하는 final String 변수
    private final String endTimeString = "23:59:59"; // 특정 날짜의 예약 정보들만 가져오기 위해서 사용하는 final String 변수

    // 현재 로그인한 사람의 아이디 관련 정보를 가져오는 함수
    private Auth.IdInterface getIdInterface() {
        Auth.IdInterface idInterface = (Auth.IdInterface) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return idInterface;
    }

    // 예약 등록
    public Reservation register(ForRegisterReservation forRegisterReservation) {
        var result = this.reservationRepository.save(forRegisterReservation.toEntity(getIdInterface().getId()));

        return result;
    }

    // 예약 승인 또는 거절하는 함수 (매개변수를 기반으로 승인인지 거절인지 결정된다.)
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

    // 입력받은 문자열을 기반으로 LocalDateTime 값을 반환
    public LocalDateTime parseLocalDateTime(String date, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        StringBuilder sb = new StringBuilder(date.substring(0, 10));
        sb.append('T');
        sb.append(time);

        return LocalDateTime.parse(sb.toString(), formatter);
    }

    // 로그인한 식당 주인이 선택한 날짜에 받은 예약 목록들을 반환
    public List<ReservationInformationInterface> getReservationForDateAndLoginOwner(Pageable pageable, String date) {
        LocalDateTime startTime = parseLocalDateTime(date, startTimeString);
        LocalDateTime endTime = parseLocalDateTime(date, endTimeString);

        var result = this.reservationRepository.findReservationForReservationDateAndOwnerId(pageable, startTime, endTime, getIdInterface().getId()).getContent();

        return result;
    }

    // 로그인한 사용자가 선택한 날짜에 했던 예약 목록들을 반환
    public List<ReservationInformationInterface> getReservationForDateAndLoginUser(Pageable pageable, String date) {
        LocalDateTime startTime = parseLocalDateTime(date, startTimeString);
        LocalDateTime endTime = parseLocalDateTime(date, endTimeString);

        var result = this.reservationRepository.findReservationForReservationDateAndUserId(pageable, startTime, endTime, getIdInterface().getId()).getContent();

        return result;
    }

    // 사용자가 자신의 예약 방문 처리를 할 때 작동되는 함수
    public Reservation visit(ForRequestReservation forRequestReservation) {
        Reservation result = this.reservationRepository.findAllByIdAndUserid(forRequestReservation.getReservationid(), getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 예약정보를 승인할 수 있는 권한이 없습니다."));
        result.visitReservation();
        this.reservationRepository.save(result);
        return result;
    }

    // 아이디를 기반으로 예약 정보를 가져오는 함수
    public ReservationInformationInterface getFromId(int id) {
        var result = this.reservationRepository.getReservationFromIdAndUserId(id, getIdInterface().getId())
                .orElseThrow(() -> new RuntimeException("현재 예약 정보가 존재하지 않거나 로그인한 계정에서 예약정보를 확인할 수 있는 권한이 없습니다."));
        return result;
    }
}
