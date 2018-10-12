package com.kakaopay.meeting_room.service;

import com.kakaopay.meeting_room.domain.MeetingRoom;
import com.kakaopay.meeting_room.dto.MeetingRoomDTO;
import com.kakaopay.meeting_room.repository.MeetingRoomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MeetingRoomServiceTest {

    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    private MeetingRoomService meetingRoomService;

    private  List<MeetingRoom> meetingRooms;

    @Before
    public void setUp() {
        MeetingRoom meetingRoomA = MeetingRoom.builder()
                .id(1L)
                .name("회의실 A")
                .location("7층 1회의실")
                .build();
        MeetingRoom meetingRoomB = MeetingRoom.builder()
                .id(2L)
                .name("회의실 B")
                .location("7층 2회의실")
                .build();
        meetingRooms = new ArrayList<>();
        meetingRooms.add(meetingRoomA);
        meetingRooms.add(meetingRoomB);
    }

    @Test
    public void list_성공() {
        when(meetingRoomRepository.findAll()).thenReturn(meetingRooms);
        List<MeetingRoomDTO> meetingRoomDTOs = meetingRoomService.list();
        assertThat(meetingRoomDTOs.size()).isEqualTo(2);
        for(MeetingRoomDTO meetingRoomDTO : meetingRoomDTOs) {
            assertThat(meetingRoomDTO.getId()).isBetween(1L, 2L);
        }
    }
}
