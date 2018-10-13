package com.kakaopay.meeting_room.service;

import com.kakaopay.meeting_room.domain.MeetingRoom;
import com.kakaopay.meeting_room.domain.Reservation;
import com.kakaopay.meeting_room.dto.ReservationDTO;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private DateFormat dateFormat;
    private Date date;
    private Reservation reservation1;
    private Reservation reservation2;
    private Reservation reservation3;

    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.parse("2018-10-19");
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .id(1L)
                .name("회의실A")
                .location("1층 1회의실")
                .build();
        reservation1 = Reservation.builder()
                .id(1L)
                .bookerName("예약자A")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .startTime("11:00")
                .endTime("12:00")
                .build();
        reservation2 = Reservation.builder()
                .id(2L)
                .bookerName("예약자B")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .endDate(dateFormat.parse("2018-10-26"))
                .startTime("15:00")
                .endTime("16:00")
                .build();
        reservation3 = Reservation.builder()
                .id(3L)
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-04"))
                .endDate(dateFormat.parse("2018-11-01"))
                .startTime("09:00")
                .endTime("10:00")
                .build();
    }

    @Test
    public void getReservationsByDate_성공() {
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        List<Reservation> repeatReservations = new ArrayList<>();
        repeatReservations.add(reservation3);

        when(reservationRepository.findByStartDate(date)).thenReturn(reservations);
        when(reservationRepository.findRepeatReservationByDate(date)).thenReturn(repeatReservations);

        List<ReservationDTO> reservationDTOs = reservationService.getReservationsByDate(date);
        assertThat(reservationDTOs.size()).isEqualTo(2);
        for(ReservationDTO reservationDTO : reservationDTOs) {
            assertThat(reservationDTO.getId()).isNotEqualTo(3L);
        }
    }
}
