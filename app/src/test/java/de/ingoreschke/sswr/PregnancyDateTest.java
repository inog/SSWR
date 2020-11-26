package de.ingoreschke.sswr;

import org.junit.Test;

import java.time.LocalDate;

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
    public void testPregnancyDate_100DaysToWait() {
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

    @Test(expected = IllegalArgumentException.class)
    public void testPregnancyDate_Date2ToBig() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(350L);
        new PregnancyDate(now, birthDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPregnancyDate_Date1ToSmall() {
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.minusDays(22L);
        new PregnancyDate(now, birthDate);
    }


}