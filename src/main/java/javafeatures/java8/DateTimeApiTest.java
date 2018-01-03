package javafeatures.java8;

import java.time.*;

import javafeatures.util.PrintUtil;

/**
 * Java 8引入了新的Date-Time API(JSR 310)来改进时间、日期的处理。
 * 
 * @author panws
 * @since 2018-01-03
 */
public class DateTimeApiTest {
	
	public static void main(String[] args) {
		
		//Clock类使用时区来返回当前的日期和纳秒时间。Clock类可以
		//替代System.currentTimeMillis()和TimeZone.getDefault()
		final Clock clock = Clock.systemUTC();
		PrintUtil.println(clock.instant());
		PrintUtil.println(clock.millis());
		
		LocalDate date = LocalDate.now();
		LocalDate dateFromClock = LocalDate.now(clock);
		PrintUtil.println(date);
		PrintUtil.println(dateFromClock);
		
		LocalTime time = LocalTime.now();
		LocalTime timeFromClock = LocalTime.now(clock);
		PrintUtil.println(time);
		PrintUtil.println(timeFromClock);
		
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime dateTimeFromClock = LocalDateTime.now(clock);
		PrintUtil.println(dateTime);
		PrintUtil.println(dateTimeFromClock);
		
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		ZonedDateTime zonedDateTimeFromClock = ZonedDateTime.now(clock);
		ZonedDateTime zonedDateTimeFromZone = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
		PrintUtil.println(zonedDateTime);
		PrintUtil.println(zonedDateTimeFromClock);
		PrintUtil.println(zonedDateTimeFromZone);
		
		//Duration类持有的时间可以精确到纳秒
		LocalDateTime from = LocalDateTime.of(2016, Month.APRIL, 16, 0, 0, 0);
		LocalDateTime to = LocalDateTime.of(2017, Month.APRIL, 16, 23, 59, 59);
		Duration duration = Duration.between(from, to);
		PrintUtil.println("Duration in days: ", duration.toDays());
		PrintUtil.println("Duration in hours: ", duration.toHours());
		PrintUtil.println("Duration in Minutes: ", duration.toMinutes());
		PrintUtil.println("Duration in Mills: ", duration.toMillis());
		PrintUtil.println("Duration in Nanos: ", duration.toNanos());
	}
}
