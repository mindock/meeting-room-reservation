package com.kakaopay.meeting_room.controller;

import com.kakaopay.meeting_room.domain.MeetingRoom;
import com.kakaopay.meeting_room.domain.Reservation;
import com.kakaopay.meeting_room.dto.ReservationDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.ObjectError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiReservationControllerTest {

    @Autowired
    private TestRestTemplate template;

    private DateFormat dateFormat;
    private MeetingRoom meetingRoom;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        meetingRoom = MeetingRoom.builder()
                .id(1L)
                .name("회의실A")
                .location("1층 1회의실")
                .build();
    }

    @Test
    public void addReservation_성공() throws ParseException {
        Reservation reservation = Reservation.builder()
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-23"))
                .startTime("12:00")
                .endTime("13:00")
                .build();
        ResponseEntity<Void> response = template.postForEntity("/api/reservation", reservation.toDTO(), Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void addReservation_중복() throws ParseException {
        Reservation reservation = Reservation.builder()
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-19"))
                .startTime("14:00")
                .endTime("17:00")
                .build();
        ResponseEntity<String> response = template.postForEntity("/api/reservation", reservation.toDTO(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("예약 시간이 겹칩니다.");
    }

    @Test
    public void addReservation_유효성_시간() throws ParseException {
        Reservation reservation = Reservation.builder()
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-31"))
                .startTime("17:00")
                .endTime("14:00")
                .build();
        ResponseEntity<List<String>> response = template.exchange(
                "/api/reservation",
                HttpMethod.POST,
                getHttpEntity(reservation.toDTO()),
                new ParameterizedTypeReference<List<String>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get(0)).isEqualTo("예약 시작시간을 끝시간보다 작게 입력해주세요.");

        reservation = Reservation.builder()
                .bookerName("예약자C")
                .meetingRoom(meetingRoom)
                .startDate(dateFormat.parse("2018-10-31"))
                .startTime("9:10")
                .endTime("10:00")
                .build();
        response = template.exchange(
                "/api/reservation",
                HttpMethod.POST,
                getHttpEntity(reservation.toDTO()),
                new ParameterizedTypeReference<List<String>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().get(0)).isEqualTo("예약시간은 hh:dd 형식, 30분 단위로 입력해주세요.");
    }

    @Test
    public void getReservationsByDate_성공() throws ParseException {
        ResponseEntity<List<ReservationDTO>> response = template.exchange(
                "/api/reservation/2018-10-19",
                HttpMethod.GET,
                getHttpEntity(),
                new ParameterizedTypeReference<List<ReservationDTO>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(4);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = dateFormat.parse("2018-10-19");
        assertThat(response.getBody().stream().filter(reservationDTO -> reservationDTO.getStartDate().equals(date1)).count()).isEqualTo(3);
    }

    @Test
    public void getReservationsByDate_데이터형식다름() {
        ResponseEntity<String> response = template.getForEntity("/api/reservation/2018.10.1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("입력받은 날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 작성)");
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(headers);
    }

    private HttpEntity getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(body, headers);
    }
}
