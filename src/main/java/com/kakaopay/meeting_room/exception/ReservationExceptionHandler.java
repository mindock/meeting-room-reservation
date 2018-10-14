package com.kakaopay.meeting_room.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ReservationExceptionHandler {

    private final static String PARSE_EXCEPTION_STR = "입력받은 날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 작성)";

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<String> invalidDateFormat(ParseException exception) {
        log.info("[ParseException] {}", PARSE_EXCEPTION_STR);
        return new ResponseEntity<>(PARSE_EXCEPTION_STR, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OverlapReservationException.class)
    public ResponseEntity<String> overlapReservation(OverlapReservationException exception) {
        log.info("[OverlapReservationException] {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> invalidReservation(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors()
                .stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
        errorMessages.stream().forEach(error -> log.info("[MethodArgumentNotValidException] {}",error));
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MeetingRoomNotFoundException.class)
    public ResponseEntity<String> invalidMeetingRoom(MeetingRoomNotFoundException exception) {
        log.info("[MeetingRoomNotFoundException] {}", exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
