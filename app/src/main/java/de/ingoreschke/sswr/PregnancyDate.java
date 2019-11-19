package de.ingoreschke.sswr;

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
	private Date d1;
	private Date d2;
	private long daysToBirth;
	private long daysUntilNow;
	private long weeksUntilNow;
	private long restOfWeekUntilNow;
	private long xteWeek;
	private long xteMonth;



	public PregnancyDate(Date date1, Date date2){
		d1 = eraseTime(date1);
		d2 = eraseTime(date2);
		daysToBirth = dateDiffInDays(d1, d2);
		daysUntilNow = GESTATION_IN_DAY - daysToBirth;
		weeksUntilNow = calcWeeks(daysUntilNow);
		restOfWeekUntilNow = calcRestOfWeek(daysUntilNow);
		xteWeek = weeksUntilNow + 1;
		xteMonth = calcMonth(weeksUntilNow) +1;
	}
	
	public Date eraseTime(Date date){
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}
	
	public long dateDiffInDays(Date date1, Date date2){
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

	
	public long calcWeeks(long days) {
		long weeks = days/7;
		return  weeks;
	}
	
	public long calcRestOfWeek(long days){
		long rest = days%7;
		return rest;
	}
	
	public long calcMonth(long weeks){
		long month = weeks/4;
		return month;
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

