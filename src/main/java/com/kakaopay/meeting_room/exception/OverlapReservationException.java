package com.kakaopay.meeting_room.exception;

public class OverlapReservationException extends RuntimeException {
    public OverlapReservationException() {
        super("Overlap Reservation Exception");
    }
    public OverlapReservationException(String msg) {
        super(msg);
    }
}
