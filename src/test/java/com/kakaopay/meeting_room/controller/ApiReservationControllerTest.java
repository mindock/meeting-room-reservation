package com.kakaopay.meeting_room.controller;

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

    private HttpEntity request;

    @Before
    public void setUp() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        request = new HttpEntity(headers);
    }

    @Test
    public void getReserfationsByDate_성공() throws ParseException {
        ResponseEntity<List<ReservationDTO>> response = template.exchange(
                "/api/reservation/2018-10-19",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ReservationDTO>>() {});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(4);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = dateFormat.parse("2018-10-19");
        assertThat(response.getBody().stream().filter(reservationDTO -> reservationDTO.getStartDate().equals(date1)).count()).isEqualTo(3);
    }

    @Test
    public void getReserfationsByDate_데이터형식다름() {
        ResponseEntity<String> response = template.getForEntity("/api/reservation/2018.10.1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("입력받은 날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 작성)");
    }
}
