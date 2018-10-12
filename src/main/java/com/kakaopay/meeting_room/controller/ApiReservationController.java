package com.kakaopay.meeting_room.controller;

import com.kakaopay.meeting_room.dto.ReservationDTO;
import com.kakaopay.meeting_room.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reservation")
public class ApiReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{date}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByDate(@PathVariable String date) throws ParseException {
        log.info("{} 날짜의 회의실 예약 현황 조회 요청", date);
        return new ResponseEntity<>(reservationService.getReservationsByDate(new SimpleDateFormat("yyyy-MM-dd").parse(date)), HttpStatus.OK);
    }
}
