package com.kakaopay.meeting_room.support;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateEntityTest {

    private DateFormat dateFormat;
    private Date date1;
    private Date date2;

    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dateFormat.parse("2018-10-03");
        date2 = dateFormat.parse("2018-10-17");
    }

    @Test
    public void addDate_성공() throws ParseException {
        assertThat(DateEntity.addDate(date1, 14)).isEqualTo(date2);
        assertThat(DateEntity.addDate(date2, 15)).isEqualTo(dateFormat.parse("2018-11-01"));
    }

    @Test
    public void trimTime_성공() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh");
        assertThat(DateEntity.trimHour(df.parse("2018-10-03 11"))).isEqualTo(date1);
        assertThat(DateEntity.trimHour(df.parse("2018-10-17 13"))).isEqualTo(date2);
    }

    @Test
    public void getDiffDate_성공() throws ParseException {
        assertThat(DateEntity.getDiffDate(date1, date2)).isEqualTo(14);
        assertThat(DateEntity.getDiffDate(dateFormat.parse("2018-09-03"), dateFormat.parse("2018-10-03"))).isEqualTo(30);
    }

    @Test
    public void getWeeks_성공() throws ParseException {
        assertThat(DateEntity.getWeeks(date1, date2)).isEqualTo(2);
        assertThat(DateEntity.getWeeks(dateFormat.parse("2018-09-03"), dateFormat.parse("2018-10-1"))).isEqualTo(4);
        assertThat(DateEntity.getWeeks(date1, null)).isEqualTo(0);
    }

    @Test
    public void changeTimeType_성공() {
        assertThat(DateEntity.changeTimeType("11:30")).isEqualTo(11.5);
        assertThat(DateEntity.changeTimeType("24:00")).isEqualTo(24);
    }
}
