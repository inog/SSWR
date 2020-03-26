package de.ingoreschke.sswr;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PregnancyDate {
    private static final String TAG = "PregnancyDate";

    static final long GESTATION_IN_DAY = 280L;
    public static final String DATE2_TOO_BIG = "Date (d2) is too far in the future.";
    public static final String DATE1_TOO_SMALL = "Date (d1) is too far in the past.";
    private long daysToBirth;
    private long daysUntilNow;
    private long weeksUntilNow;
    private long restOfWeekUntilNow;
    private long xteWeek;
    private long xteMonth;

    public PregnancyDate(LocalDate date1, LocalDate date2) {
        daysToBirth = dateDiffInDays(date1,date2);

        daysUntilNow = GESTATION_IN_DAY - daysToBirth;
        weeksUntilNow = calcWeeks(daysUntilNow);
        restOfWeekUntilNow = calcRestOfWeek(daysUntilNow);
        xteWeek = weeksUntilNow + 1;
        xteMonth = calcMonth(weeksUntilNow) + 1;
    }

    private long dateDiffInDays(LocalDate date1, LocalDate date2) {
        long days = ChronoUnit.DAYS.between(date1, date2);
        if (days > GESTATION_IN_DAY + 22L) {
            throw new IllegalArgumentException(DATE2_TOO_BIG);
        } else if (days < -21L) {
            throw new IllegalArgumentException(DATE1_TOO_SMALL);
        }
        return days;
    }

    private long calcWeeks(long days) {
        return days / 7;
    }

    private long calcRestOfWeek(long days) {
        return days % 7;
    }

    private long calcMonth(long weeks) {
        return weeks / 4;
    }

    //getter
    public long getDaysToBirth() {
        return daysToBirth;
    }

    public long getDaysUntilNow() {
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

