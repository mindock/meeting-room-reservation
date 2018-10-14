package com.kakaopay.meeting_room.dto;

import com.kakaopay.meeting_room.support.DateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationDTO {

    private final static String TIME_REGEX = "([0-9]{1,2}):(00|30)";

    private Long id;
    private MeetingRoomDTO meetingRoom;

    @NotBlank(message = "예약자명을 입력해주세요.")
    @Size(min = 2, max = 10, message = "예약자명은 2자 이상 10자 이하로 입력해주세요.")
    private String bookerName;

    @NotNull(message = "예약날짜를 입력해주세요.")
    private Date startDate;

    @NotBlank(message = "예약 시작시간을 입력해주세요.")
    @Pattern(regexp = TIME_REGEX, message = "예약시간은 hh:dd 형식, 30분 단위로 입력해주세요.")
    private String startTime;

    @NotBlank(message = "예약 끝시간을 입력해주세요.")
    @Pattern(regexp = TIME_REGEX, message = "예약시간은 hh:dd 형식, 30분 단위로 입력해주세요.")
    private String endTime;

    @Min(value = 0, message = "예약 반복횟수는 0 이상으로 입력해주세요.")
    @Max(value = 10,  message = "예약 반복횟수는 10 이하로 입력해주세요.")
    private int repeatNum;

    @AssertTrue(message = "예약 시작시간을 끝시간보다 작게 입력해주세요.")
    public boolean isRightReservationTime() {
        return DateEntity.changeTimeType(startTime) < DateEntity.changeTimeType(endTime);
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }
}
