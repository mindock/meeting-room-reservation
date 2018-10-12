package com.kakaopay.meeting_room.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.ParseException;

@Slf4j
@ControllerAdvice
public class ReservationExceptionHandler {

    private final static String PARSE_EXCEPTION_STR = "입력받은 날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 작성)";

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> invaildDateFormat(ParseException exception) {
        log.info("[ParseException] {}", PARSE_EXCEPTION_STR);
        return new ResponseEntity<>(PARSE_EXCEPTION_STR, HttpStatus.BAD_REQUEST);
    }
}
