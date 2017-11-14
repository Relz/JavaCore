package main.java.ru.relz.javacore2017.datetime;

public class DateTimeHelper {
	private DateTimeHelper() { }

	public final static long HOURS_IN_DAY = 24;
	public final static long MINUTES_IN_HOUR = 60;
	public final static long SECONDS_IN_MINUTE = 60;
	public final static long MILLISECONDS_IN_SECOND = 1000;
	public final static long SECONDS_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE;
	public final static long MILLISECONDS_IN_DAY = MILLISECONDS_IN_SECOND * SECONDS_IN_DAY;
}
