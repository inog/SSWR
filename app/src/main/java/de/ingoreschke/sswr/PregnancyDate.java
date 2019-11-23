package de.ingoreschke.sswr;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

public class PregnancyDate {
	private static final String TAG = "PregnancyDate";
	private static final double MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
	static final long GESTATION_IN_DAY = 280L;
	public static final String DATE2_TOO_BIG = "Date (d2) is too far in the future.";
	public static final String DATE1_TOO_SMALL = "Date (d1) is too far in the past.";
    private long daysToBirth;
	private long daysUntilNow;
	private long weeksUntilNow;
	private long restOfWeekUntilNow;
	private long xteWeek;
	private long xteMonth;



	public PregnancyDate(Date date1, Date date2){

    //    Date d2 = eraseTime(date2);
	//	Date d1 = eraseTime(date1);
	//	daysToBirth = dateDiffInDays(d1, d2);

		LocalDate ld1 = LocalDate.from(date1.toInstant().atZone(ZoneId.systemDefault()));
		LocalDate ld2 = LocalDate.from(date2.toInstant().atZone(ZoneId.systemDefault()));
		daysToBirth = dateDiffInDays(ld1, ld2);

		daysUntilNow = GESTATION_IN_DAY - this.daysToBirth;
		weeksUntilNow = calcWeeks(daysUntilNow);
		restOfWeekUntilNow = calcRestOfWeek(daysUntilNow);
		xteWeek = weeksUntilNow + 1;
		xteMonth = calcMonth(weeksUntilNow) +1;
	}

	private Date eraseTime(Date date){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	private long dateDiffInDays(LocalDate date1, LocalDate date2){
		Period intervalPeriod = Period.between(date1, date2);
		int days = intervalPeriod.getDays();
		if (days > GESTATION_IN_DAY + 22L){
			throw new IllegalArgumentException(DATE2_TOO_BIG);
		}else if(days < -21L){
			throw new IllegalArgumentException(DATE1_TOO_SMALL);
		}
		return days;
	}
	
	private long dateDiffInDays(Date date1, Date date2){
		Calendar c1 = GregorianCalendar.getInstance();
		Calendar c2 = GregorianCalendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		long md1 = c1.getTimeInMillis();
		long md2 = c2.getTimeInMillis();
		long milliseconds = md2 - md1;
		long days = Math.round(milliseconds / MILLISECONDS_IN_DAY);
		Log.d(TAG, "dateDiffInDays : " + date1.toString() + "\t" + date2.toString() + "\t" + days) ;


		if (days > GESTATION_IN_DAY + 22L){
			throw new IllegalArgumentException(DATE2_TOO_BIG);
		}else if(days < -21L){
			throw new IllegalArgumentException(DATE1_TOO_SMALL);
		}
		return days;
	}

	private long calcWeeks(long days) {
        return days/7;
	}
	
	private long calcRestOfWeek(long days){
        return days%7;
	}
	
	private long calcMonth(long weeks){
        return weeks/4;
	}

    //getter
	public long getDaysToBirth() {
		return daysToBirth;
	}
	
	public long getDaysUntilNow(){
		return daysUntilNow;
	}

	public long getWeeksUntilNow() {
		return weeksUntilNow;
	}
	
	public long getRestOfWeekUntilNow() {
		return restOfWeekUntilNow;
	}

	public long getXteWeek() {
		return xteWeek;
	}
	
	public long getXteMonth() {
		return xteMonth;
	}
}

