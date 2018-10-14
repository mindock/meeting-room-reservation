package com.kakaopay.meeting_room.exception;

public class MeetingRoomNotFoundException extends RuntimeException {
    public MeetingRoomNotFoundException() {
        super("meetingRoom not found");
    }
    public MeetingRoomNotFoundException(String msg) {
        super(msg);
    }
}