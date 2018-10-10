package com.kakaopay.meeting_room.repository;

import com.kakaopay.meeting_room.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
