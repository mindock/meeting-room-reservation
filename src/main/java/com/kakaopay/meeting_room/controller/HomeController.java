package com.kakaopay.meeting_room.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("")
    public String index() {
        log.info("[메인 페이지] 예약 현황 조회 페이지 진입");
        return "reservation-timetable";
    }
}
