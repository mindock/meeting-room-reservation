package com.kakaopay.meeting_room.dto;

import com.kakaopay.meeting_room.domain.MeetingRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationDTO {
    private Long id;

    private MeetingRoomDTO meetingRoom;
    private String bookerName;

    private Date startDate;

    private String startTime;
    private String endTime;

    private int repeatNum;
}
