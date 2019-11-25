package de.ingoreschke.sswr;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PregnancyDateTest {

    @Test
    public void testPregnancyDate() {
        long expectedDays2Birth = 10L;
        long expectedDaysUntilNow = PregnancyDate.GESTATION_IN_DAY - expectedDays2Birth;
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(expectedDays2Birth);

        Date startDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        PregnancyDate cut = new PregnancyDate(startDate, dayOfBirth);

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

        Date startDate = new Date();
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        PregnancyDate cut = new PregnancyDate(startDate, dayOfBirth);

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

        Date startDate = new Date();
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        PregnancyDate cut = new PregnancyDate(startDate, dayOfBirth);

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
        Date startDate = new Date();
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        new PregnancyDate(startDate, dayOfBirth);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testPregnancyDate_Date1ToSmall() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.minusDays(22L);
        Date startDate = new Date();
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        new PregnancyDate(startDate, dayOfBirth);
    }
}