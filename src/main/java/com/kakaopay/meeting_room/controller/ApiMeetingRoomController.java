package com.kakaopay.meeting_room.controller;

import com.kakaopay.meeting_room.dto.MeetingRoomDTO;
import com.kakaopay.meeting_room.service.MeetingRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/meetingRoom")
public class ApiMeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @RequestMapping("")
    public ResponseEntity<List<MeetingRoomDTO>> list() {
        log.info("회의실의 전체 리스트 조회");
        return new ResponseEntity<>(meetingRoomService.list(), HttpStatus.OK);
    }
}
