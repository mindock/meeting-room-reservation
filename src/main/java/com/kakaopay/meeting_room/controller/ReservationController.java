package com.kakaopay.meeting_room.controller;

import com.kakaopay.meeting_room.service.MeetingRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping("/")
    public String reservationFormDefault(Model model) {
        log.info("기본 예약하기 페이지 진입");
        model.addAttribute("meetingRooms", meetingRoomService.list());
        return "reservation-create";
    }

    @GetMapping("/{date}")
    public String reservationForm(@PathVariable String date, Model model) {
        log.info("날짜 입력된 예약하기 페이지 진입");
        model.addAttribute("meetingRooms", meetingRoomService.list());
        model.addAttribute("reservationDate", date);
        return "reservation-create";
    }
}
