package com.kakaopay.meeting_room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MeetingRoomDTO {
    private Long id;
    private String name;
    private String location;
}
