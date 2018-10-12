package com.kakaopay.meeting_room.service;

import com.kakaopay.meeting_room.domain.Reservation;
import com.kakaopay.meeting_room.dto.ReservationDTO;
import com.kakaopay.meeting_room.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservationsByDate(Date date) {
        List<Reservation> reservations = reservationRepository.findByStartDate(date);
        reservations.addAll(reservationRepository.findRepeatReservationByDate(date)
                .stream()
                .filter(reservation -> reservation.isReservationDate(date))
                .collect(Collectors.toList()));
        return reservations.stream().map(reservation -> reservation.toDTO()).collect(Collectors.toList());
    }
}
