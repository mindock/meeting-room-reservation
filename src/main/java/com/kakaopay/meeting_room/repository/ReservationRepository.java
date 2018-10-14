package com.kakaopay.meeting_room.repository;

import com.kakaopay.meeting_room.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStartDate(Date date);
    List<Reservation> findByEndDateNotNullAndStartDateLessThanAndEndDateGreaterThanEqual(Date startDate, Date endDate);

    List<Reservation> findByStartDateAndMeetingRoomId(Date date, Long id);
    List<Reservation> findByEndDateNotNullAndStartDateLessThanAndEndDateGreaterThanEqualAndMeetingRoomId(Date startDate, Date endDate, Long id);

    default List<Reservation> findRepeatReservationByDate(Date date) {
        return findByEndDateNotNullAndStartDateLessThanAndEndDateGreaterThanEqual(date, date);
    }

    default List<Reservation> findRepeatReservationByDateAndMeetingRoom(Date date, Long id) {
        return findByEndDateNotNullAndStartDateLessThanAndEndDateGreaterThanEqualAndMeetingRoomId(date, date, id);
    }
}