package com.kakaopay.meeting_room.service;

import com.kakaopay.meeting_room.dto.MeetingRoomDTO;
import com.kakaopay.meeting_room.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Cacheable(value = "MeetingRoomList")
    public List<MeetingRoomDTO> list() {
        return meetingRoomRepository.findAll()
                .stream().map(meetingRoom -> meetingRoom.toDTO()).collect(Collectors.toList());
    }
}
