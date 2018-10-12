package com.kakaopay.meeting_room.domain;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    private DateFormat dateFormat;
    private Date date1;
    private Date date2;
    private Reservation reservation;

    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dateFormat.parse("2018-10-03");
        date2 = dateFormat.parse("2018-10-17");
        MeetingRoom meetingRoom = MeetingRoom.builder()
                .id(1L)
                .name("회의실 A")
                .location("8층 1회의실")
                .build();
        reservation = Reservation.builder()
                .id(1L)
                .meetingRoom(meetingRoom)
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
    public void getEndDate_성공() throws ParseException {
        Date endDate = Reservation.getEndDate(date1, 2);
        assertThat(endDate).hasYear(2018);
        assertThat(endDate).hasMonth(10);
        assertThat(endDate).hasDayOfMonth(17);

        endDate = Reservation.getEndDate(date1, 5);
        assertThat(endDate).hasYear(2018);
        assertThat(endDate).hasMonth(11);
        assertThat(endDate).hasDayOfMonth(7);

        assertThat(Reservation.getEndDate(date1, 0)).isNull();
    }

    @Test
    public void getDiffDate_성공() throws ParseException {
        assertThat(Reservation.getDiffDate(date1, date2)).isEqualTo(14);
        assertThat(Reservation.getDiffDate(dateFormat.parse("2018-09-03"), dateFormat.parse("2018-10-03"))).isEqualTo(30);
    }

    @Test
    public void getRepeatNumber_성공() throws ParseException {
        assertThat(Reservation.getRepeatNumber(date1, date2)).isEqualTo(2);
        assertThat(Reservation.getRepeatNumber(dateFormat.parse("2018-09-03"), dateFormat.parse("2018-10-1"))).isEqualTo(4);
        assertThat(Reservation.getRepeatNumber(date1, null)).isEqualTo(0);
    }
}
