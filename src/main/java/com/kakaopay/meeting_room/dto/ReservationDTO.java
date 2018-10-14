package com.kakaopay.meeting_room.dto;

import com.kakaopay.meeting_room.support.DateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.util.Date;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationDTO {

    private final static String START_TIME_REGEX = "(9|1[0-9]|20):(00|30)";
    private final static String END_TIME_REGEX = "9:30|((1[0-9]|20):(00|30))|21:00";

    private Long id;
    private MeetingRoomDTO meetingRoom;

    @NotBlank(message = "예약자명을 입력해주세요.")
    @Size(min = 2, max = 10, message = "예약자명은 2자 이상 10자 이하로 입력해주세요.")
    private String bookerName;

    @NotNull(message = "예약날짜를 입력해주세요.")
    private Date startDate;

    @NotBlank(message = "예약 시작시간을 입력해주세요.")
    @Pattern(regexp = START_TIME_REGEX, message = "예약시간은 hh:dd 형식, 30분 단위로 입력해주세요.")
    private String startTime;

    @NotBlank(message = "예약 끝시간을 입력해주세요.")
    @Pattern(regexp = END_TIME_REGEX, message = "예약시간은 hh:dd 형식, 30분 단위로 입력해주세요.")
    private String endTime;

    @Min(value = 0, message = "예약 반복횟수는 0 이상으로 입력해주세요.")
    @Max(value = 10,  message = "예약 반복횟수는 10 이하로 입력해주세요.")
    private int repeatNum;

    @AssertTrue(message = "예약 시작시간을 끝시간보다 작게 입력해주세요.")
    public boolean isRightReservationTime() {
        return DateEntity.changeTimeType(startTime) < DateEntity.changeTimeType(endTime);
    }

    @AssertTrue(message = "예약 날짜를 오늘이거나 미래를 입력해주세요.")
    public boolean isTodayOrAfterThanToday() {
        Date startday = DateEntity.trimTime(startDate);
        Date today = DateEntity.trimTime(DateEntity.getToday());
        return startday.after(today) || startday.equals(today);
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }
}
