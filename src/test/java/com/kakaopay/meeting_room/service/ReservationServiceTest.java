package com.kakaopay.meeting_room.service;

import com.kakaopay.meeting_room.domain.MeetingRoom;
import com.kakaopay.meeting_room.domain.Reservation;
import com.kakaopay.meeting_room.dto.ReservationDTO;
import com.kakaopay.meeting_room.exception.OverlapReservationException;
import com.kakaopay.meeting_room.repository.MeetingRoomRepository;
import com.kakaopay.meeting_room.repository.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    private ReservationService reservationService;

    private DateFormat dateFormat;
    private Date date;
    private MeetingRoom meetingRoom;
    private List<Reservation> reservations;
    private List<Reservation> repeatReservations;


    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.parse("2018-10-19");
        meetingRoom = MeetingRoom.builder()
                .id(1L)
                .name("회의실A")
                .location("1층 1회의실")
                .build();
        Reservation reservation1 = Reservation.builder()
                .id(1L)
                .bookerName("예약자A")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .startTime("11:00")
                .endTime("12:00")
                .build();
        Reservation reservation2 = Reservation.builder()
                .id(2L)
                .bookerName("예약자B")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .endDate(dateFormat.parse("2018-10-26"))
                .startTime("15:00")
                .endTime("16:00")
                .build();
        Reservation reservation3 = Reservation.builder()
                .id(3L)
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-04"))
                .endDate(dateFormat.parse("2018-11-01"))
                .startTime("09:00")
                .endTime("10:00")
                .build();

        reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        repeatReservations = new ArrayList<>();
        repeatReservations.add(reservation3);
    }

    @Test
    public void addReservation_성공() throws ParseException {
        when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(meetingRoom));
        when(reservationRepository.findByStartDateAndMeetingRoomId(date, 1L)).thenReturn(reservations);
        when(reservationRepository.findRepeatReservationByDateAndMeetingRoom(date, 1L)).thenReturn(repeatReservations);

        Reservation reservation = Reservation.builder()
                .id(4L)
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .startTime("09:00")
                .endTime("10:00")
                .build();
        reservationService.addReservation(reservation.toDTO());
    }

    @Test(expected = OverlapReservationException.class)
    public void addReservation_실패() throws ParseException {
        when(meetingRoomRepository.findById(1L)).thenReturn(Optional.of(meetingRoom));
        when(reservationRepository.findByStartDateAndMeetingRoomId(date, 1L)).thenReturn(reservations);
        when(reservationRepository.findRepeatReservationByDateAndMeetingRoom(date, 1L)).thenReturn(repeatReservations);

        Reservation reservation = Reservation.builder()
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .startTime("15:00")
                .endTime("15:30")
                .build();
        reservationService.addReservation(reservation.toDTO());
    }

    @Test
    public void getReservationsByDate_성공() {
        when(reservationRepository.findByStartDate(date)).thenReturn(reservations);
        when(reservationRepository.findRepeatReservationByDate(date)).thenReturn(repeatReservations);

        List<ReservationDTO> reservationDTOs = reservationService.getReservationsByDate(date);
        assertThat(reservationDTOs.size()).isEqualTo(2);
        for(ReservationDTO reservationDTO : reservationDTOs) {
            assertThat(reservationDTO.getId()).isNotEqualTo(3L);
        }
    }
}
