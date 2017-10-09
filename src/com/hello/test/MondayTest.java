package com.hello.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class MondayTest {

	public static void main(String[] args) throws ParseException {

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2017-09-02");
		System.out.println("given date:"+date);
		Timestamp tim = new Timestamp(date.getTime());
		LocalDate local = tim.toLocalDateTime().toLocalDate();
		TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
		local = local.with(fieldISO, 2);
		System.out.println("monday:"+local);
	}

}
