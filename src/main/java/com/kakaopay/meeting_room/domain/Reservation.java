package com.kakaopay.meeting_room.domain;

import com.kakaopay.meeting_room.dto.ReservationDTO;
import com.kakaopay.meeting_room.support.DateEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    public Reservation(Long id, MeetingRoom meetingRoom, @NotNull String bookerName, @NotNull Date createDate,
                       @NotNull Date startDate, Date endDate, @NotNull String startTime, @NotNull String endTime) {
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

        if(DateEntity.getDiffDate(this.startDate, date) % 7 == 0)
            return true;
        return false;
    }

    private void setEndDate(int repeatNumber) {
        if(repeatNumber == 0) {
            this.endDate = null;
        }else {
            this.endDate = DateEntity.addDate(startDate, 7 * repeatNumber);
        }
    }

    public static Reservation ofDTO(ReservationDTO reservationDTO) {
        Reservation reservation = Reservation.builder()
                .id(reservationDTO.getId())
                .meetingRoom(MeetingRoom.ofDTO(reservationDTO.getMeetingRoom()))
                .bookerName(reservationDTO.getBookerName())
                .createDate(new Date())
                .startDate(reservationDTO.getStartDate())
                .startTime(reservationDTO.getStartTime())
                .endTime(reservationDTO.getEndTime())
                .build();
        reservation.setEndDate(reservationDTO.getRepeatNum());
        return reservation;
    }

    public ReservationDTO toDTO() {
        return new ReservationDTO(id, meetingRoom.toDTO(), bookerName, startDate, startTime, endTime, DateEntity.getWeeks(startDate, endDate));
    }

    public boolean isOverlap(List<Reservation> reservations) {
        log.debug("겹친 예약의 갯수 확인하기");
        return reservations.stream()
                .filter(reservation -> isOverlapTime(reservation)).count() != 0;
    }

    private boolean isOverlapTime(Reservation reservation) {
        log.debug("시간 비교 [this] start: {}, end: {}  [other] start: {}, end: {}",
                DateEntity.changeTimeType(this.startTime), DateEntity.changeTimeType(this.endTime),
                DateEntity.changeTimeType(reservation.startTime), DateEntity.changeTimeType(reservation.endTime));
        if(DateEntity.changeTimeType(this.startTime) >= DateEntity.changeTimeType(reservation.endTime)
                || DateEntity.changeTimeType(this.endTime) <= DateEntity.changeTimeType(reservation.startTime))
            return false;
        return true;
    }
}