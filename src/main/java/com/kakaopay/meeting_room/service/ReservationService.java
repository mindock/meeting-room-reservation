package com.kakaopay.meeting_room.service;

import com.kakaopay.meeting_room.domain.Reservation;
import com.kakaopay.meeting_room.dto.MeetingRoomDTO;
import com.kakaopay.meeting_room.dto.ReservationDTO;
import com.kakaopay.meeting_room.exception.MeetingRoomNotFoundException;
import com.kakaopay.meeting_room.exception.OverlapReservationException;
import com.kakaopay.meeting_room.repository.MeetingRoomRepository;
import com.kakaopay.meeting_room.repository.ReservationRepository;
import com.kakaopay.meeting_room.support.DateEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    public List<ReservationDTO> getReservationsByDate(Date date) {
        List<Reservation> reservations = reservationRepository.findByStartDate(date);
        reservations.addAll(reservationRepository.findRepeatReservationByDate(date)
                .stream()
                .filter(reservation -> reservation.isReservationDate(date))
                .collect(Collectors.toList()));
        return reservations.stream().map(reservation -> reservation.toDTO()).collect(Collectors.toList());
    }

    public synchronized void addReservation(ReservationDTO reservationDTO) {
        checkExistedMeetingRoom(reservationDTO.getMeetingRoom());
        reservationDTO.setStartDate(DateEntity.trimTime(reservationDTO.getStartDate()));
        if(checkOverlap(reservationDTO))
            throw new OverlapReservationException("예약 시간이 겹칩니다.");
        reservationRepository.save(Reservation.ofDTO(reservationDTO));
    }

    private void checkExistedMeetingRoom(MeetingRoomDTO meetingRoomDTO) {
        if(!meetingRoomRepository.findById(meetingRoomDTO.getId()).isPresent())
            throw new MeetingRoomNotFoundException("회의실이 존재하지 않습니다.");
    }

    private boolean checkOverlap(ReservationDTO reservationDTO) {
        Date date = reservationDTO.getStartDate();
        int repeat = reservationDTO.getRepeatNum();
        Reservation reservation = Reservation.ofDTO(reservationDTO);
        do {
            if(reservation.isOverlap(getReservationsByDateAndMeetingRoom(date, reservationDTO.getMeetingRoom())))
                return true;
            date = DateEntity.addDate(date, 7);
            repeat--;
        } while(repeat >= 0);
        return false;
    }

    private List<Reservation> getReservationsByDateAndMeetingRoom(Date date, MeetingRoomDTO meetingRoomDTO) {
        List<Reservation> reservations = reservationRepository.findByStartDateAndMeetingRoomId(date, meetingRoomDTO.getId());
        reservations.addAll(reservationRepository.findRepeatReservationByDateAndMeetingRoom(date, meetingRoomDTO.getId())
                .stream()
                .filter(reservation -> reservation.isReservationDate(date))
                .collect(Collectors.toList()));
        return reservations;
    }
}
