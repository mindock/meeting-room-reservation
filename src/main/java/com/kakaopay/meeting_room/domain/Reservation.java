package com.kakaopay.meeting_room.domain;

import com.kakaopay.meeting_room.dto.ReservationDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Entity
@NoArgsConstructor
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

    @Builder
    public Reservation(Long id, MeetingRoom meetingRoom, @NotNull String bookerName, @NotNull Date createDate, @NotNull Date startDate, Date endDate, @NotNull String startTime, @NotNull String endTime) {
        this.id = id;
        this.meetingRoom = meetingRoom;
        this.bookerName = bookerName;
        this.createDate = createDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isReservationDate(Date date) {
        if(endDate == null || startDate.after(date) || endDate.before(date))
            return false;

        if(getDiffDate(this.startDate, date) % 7 == 0)
            return true;
        return false;
    }

    public static Date getEndDate(Date startDate, int repeatNumber) {
        if(repeatNumber == 0)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, 7*repeatNumber);
        return calendar.getTime();
    }

    public static long getDiffDate(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) / (24*60*60*1000);
    }

    public static int getRepeatNumber(Date startDate, Date endDate) {
        if(endDate == null)
            return 0;
        return (int)getDiffDate(startDate, endDate) / 7;
    }

    public static Reservation ofDTO(ReservationDTO reservationDTO) {
        return Reservation.builder()
                .id(reservationDTO.getId())
                .meetingRoom(MeetingRoom.ofDTO(reservationDTO.getMeetingRoom()))
                .bookerName(reservationDTO.getBookerName())
                .createDate(new Date())
                .startDate(reservationDTO.getStartDate())
                .endDate(getEndDate(reservationDTO.getStartDate(), reservationDTO.getRepeatNum()))
                .startTime(reservationDTO.getStartTime())
                .endTime(reservationDTO.getEndTime())
                .build();
    }

    public ReservationDTO toDTO() {
        return new ReservationDTO(id, meetingRoom.toDTO(), bookerName, startDate, startTime, endTime, getRepeatNumber(startDate, endDate));
    }

    public boolean isOverlap(List<Reservation> reservations) {
        log.debug("겹친 예약의 갯수 확인하기");
        return reservations.stream()
                .filter(reservation -> isOverlapTime(reservation)).count() != 0;
    }

    private boolean isOverlapTime(Reservation reservation) {
        log.debug("시간 비교 [this] start: {}, end: {}  [other] start: {}, end: {}", changeTimeType(this.startTime), changeTimeType(this.endTime),
                changeTimeType(reservation.startTime), changeTimeType(reservation.endTime));
        if(changeTimeType(this.startTime) >= changeTimeType(reservation.endTime)
                || changeTimeType(this.endTime) <= changeTimeType(reservation.startTime))
            return false;
        return true;
    }

    private double changeTimeType(String time) {
        String[] split = time.split(":");
        if(split[1].equals("00"))
            return Integer.parseInt(split[0]);
        return Integer.parseInt(split[0]) + 0.5;
    }
}