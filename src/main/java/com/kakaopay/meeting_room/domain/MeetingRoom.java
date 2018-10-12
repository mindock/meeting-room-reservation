package com.kakaopay.meeting_room.domain;

import com.kakaopay.meeting_room.dto.MeetingRoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
public class MeetingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String location;

    @Builder
    public MeetingRoom(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public static MeetingRoom ofDTO(MeetingRoomDTO meetingRoomDTO) {
        return MeetingRoom.builder()
                .id(meetingRoomDTO.getId())
                .name(meetingRoomDTO.getName())
                .location(meetingRoomDTO.getLocation())
                .build();
    }

    public MeetingRoomDTO toDTO() {
        return new MeetingRoomDTO(this.id, this.name, this.location);
    }
}
