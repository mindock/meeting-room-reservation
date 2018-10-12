package com.kakaopay.meeting_room.repository;

import com.kakaopay.meeting_room.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStartDate(Date date);

    List<Reservation> findByEndDateNotNullAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startDate, Date endDate);

    default List<Reservation> findRepeatReservationByDate(Date date) {
        return findByEndDateNotNullAndStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date);
    }
}