package com.kakaopay.meeting_room.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private MeetingRoom meetingRoom;
    @NotNull
    private String bookerName;
    @NotNull
    private Date createDate;

    @NotNull
    private Date startDate;
    private Date endDate;
    @NotNull
    private String startTime;
    @NotNull
    private String endTime;
}