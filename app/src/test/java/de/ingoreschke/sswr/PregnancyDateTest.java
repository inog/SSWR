package de.ingoreschke.sswr;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PregnancyDateTest {

    @Test
    public void testPregnancyDate() {
        long expectedDays2Birth = 10L;
        long expectedDaysUntilNow = PregnancyDate.GESTATION_IN_DAY - expectedDays2Birth;
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(expectedDays2Birth);

        PregnancyDate cut = new PregnancyDate(now, birthDate);

        assertEquals(expectedDays2Birth, cut.getDaysToBirth());
        assertEquals(expectedDaysUntilNow, cut.getDaysUntilNow());
        assertEquals(4L, cut.getRestOfWeekUntilNow());
        assertEquals(38L, cut.getWeeksUntilNow());
        assertEquals(39L, cut.getXteWeek());
        assertEquals(10L, cut.getXteMonth());
    }


    @Test
    public void testPregnancyDate_differentTime() {
        long expectedDays2Birth = 10L;
        long expectedDaysUntilNow = PregnancyDate.GESTATION_IN_DAY - expectedDays2Birth;
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(expectedDays2Birth);

        PregnancyDate cut = new PregnancyDate(now, birthDate);

        assertEquals(expectedDays2Birth, cut.getDaysToBirth());
        assertEquals(expectedDaysUntilNow, cut.getDaysUntilNow());
        assertEquals(4L, cut.getRestOfWeekUntilNow());
        assertEquals(38L, cut.getWeeksUntilNow());
        assertEquals(39L, cut.getXteWeek());
        assertEquals(10L, cut.getXteMonth());
    }

    @Test
    public void testPregnancyDate_later() {
        long expectedDays2Birth = 100L;
        long expectedDaysUntilNow = PregnancyDate.GESTATION_IN_DAY - expectedDays2Birth;
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(expectedDays2Birth);

        PregnancyDate cut = new PregnancyDate(now, birthDate);

        assertEquals(expectedDays2Birth, cut.getDaysToBirth());
        assertEquals(expectedDaysUntilNow, cut.getDaysUntilNow());
        assertEquals(5L, cut.getRestOfWeekUntilNow());
        assertEquals(25L, cut.getWeeksUntilNow());
        assertEquals(26L, cut.getXteWeek());
        assertEquals(7, cut.getXteMonth());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPregnancyDate_Date2ToBig() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(350L);
        new PregnancyDate(now, birthDate);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPregnancyDate_Date1ToSmall() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.minusDays(22L);
        new PregnancyDate(now,birthDate);
    }

    @Test
    public void testCalenderDate(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.YEAR) + " - " + c.get(Calendar.MONTH) + " - " + c.get(Calendar.DAY_OF_MONTH));
        System.out.println(c.getTime());
        System.out.println(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)));
        System.out.println(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH))); //need +1 for no Zero Month
    }
}