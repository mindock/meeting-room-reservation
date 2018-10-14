package com.kakaopay.meeting_room.domain;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    private DateFormat dateFormat;
    private Date date1;
    private Date date2;
    private MeetingRoom meetingRoom1;
    private MeetingRoom meetingRoom2;
    private Reservation reservation;

    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dateFormat.parse("2018-10-03");
        date2 = dateFormat.parse("2018-10-17");
        meetingRoom1 = MeetingRoom.builder()
                .id(1L)
                .name("회의실 A")
                .location("8층 1회의실")
                .build();
        meetingRoom2 = MeetingRoom.builder()
                .id(2L)
                .name("회의실 B")
                .location("8층 3회의실")
                .build();
        reservation = Reservation.builder()
                .id(1L)
                .meetingRoom(meetingRoom1)
                .bookerName("박선영")
                .startDate(date1)
                .endDate(date2)
                .startTime("11:00")
                .endTime("13:00")
                .build();
    }

    @Test
    public void isReservationDate_성공() throws ParseException {
        assertThat(reservation.isReservationDate(dateFormat.parse("2018-10-03"))).isTrue();
        assertThat(reservation.isReservationDate(dateFormat.parse("2018-10-10"))).isTrue();
        assertThat(reservation.isReservationDate(dateFormat.parse("2018-10-17"))).isTrue();
        assertThat(reservation.isReservationDate(dateFormat.parse("2018-10-15"))).isFalse();
        assertThat(reservation.isReservationDate(dateFormat.parse("2018-10-09"))).isFalse();
        assertThat(reservation.isReservationDate(dateFormat.parse("2018-10-24"))).isFalse();
    }

    @Test
    public void isOverlap_겹치지않음() {
        Reservation testReservation = Reservation.builder()
                .id(2L)
                .meetingRoom(meetingRoom2)
                .bookerName("박선영")
                .startDate(date1)
                .startTime("15:00")
                .endTime("16:00")
                .build();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        assertThat(testReservation.isOverlap(reservations)).isFalse();

        Reservation testReservation2 = Reservation.builder()
                .id(3L)
                .meetingRoom(meetingRoom2)
                .bookerName("박선영")
                .startDate(date1)
                .startTime("18:00")
                .endTime("18:30")
                .build();
        reservations.add(testReservation);
        assertThat(testReservation2.isOverlap(reservations)).isFalse();
    }

    @Test
    public void isOverlap_회의실겹침_시간다름() {
        Reservation testReservation = Reservation.builder()
                .id(2L)
                .meetingRoom(meetingRoom1)
                .bookerName("박선영")
                .startDate(date2)
                .startTime("15:00")
                .endTime("16:00")
                .build();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        assertThat(testReservation.isOverlap(reservations)).isFalse();

        Reservation testReservation2 = Reservation.builder()
                .id(3L)
                .meetingRoom(meetingRoom1)
                .bookerName("박선영")
                .startDate(date1)
                .endDate(date2)
                .startTime("18:00")
                .endTime("18:30")
                .build();
        reservations.add(testReservation);
        assertThat(testReservation2.isOverlap(reservations)).isFalse();
    }

    @Test
    public void isOverlap_회의실겹침_시간겹침() {
        Reservation testReservation = Reservation.builder()
                .id(2L)
                .meetingRoom(meetingRoom1)
                .bookerName("박선영")
                .startDate(date2)
                .startTime("12:00")
                .endTime("12:30")
                .build();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        assertThat(testReservation.isOverlap(reservations)).isTrue();

        Reservation testReservation2 = Reservation.builder()
                .id(3L)
                .meetingRoom(meetingRoom1)
                .bookerName("박선영")
                .startDate(date1)
                .endDate(date2)
                .startTime("11:00")
                .endTime("12:30")
                .build();
        reservations.add(testReservation);
        assertThat(testReservation2.isOverlap(reservations)).isTrue();
    }
}
