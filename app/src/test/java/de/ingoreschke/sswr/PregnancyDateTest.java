package de.ingoreschke.sswr;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PregnancyDateTest {
    private PregnancyDate cut;

    @Test
    public void testPregnancyDate() {
        long expectedDays2Birth = 10L;
        long expectedDaysUntilNow = PregnancyDate.GESTATION_IN_DAY - expectedDays2Birth;
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(expectedDays2Birth);

        Date startDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cut = new PregnancyDate(startDate, dayOfBirth);

        assertEquals(expectedDays2Birth, cut.getDaysToBirth());
        assertEquals(expectedDaysUntilNow, cut.getDaysUntilNow());
        assertEquals(4L, cut.getRestOfWeekUntilNow());
        assertEquals(38L, cut.getWeeksUntilNow());
        assertEquals(39L, cut.getXteWeek());
        assertEquals(10L, cut.getXteMonth());
    }
}