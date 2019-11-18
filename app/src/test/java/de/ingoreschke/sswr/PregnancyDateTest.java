package de.ingoreschke.sswr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;



@RunWith(MockitoJUnitRunner.class)
public class PregnancyDateTest {
     PregnancyDate cut;

    @Test
    public void testPregnancyDate(){
        LocalDate now = LocalDate.now();
        LocalDate birthDate = now.plusDays(10);

        Date startDate = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dayOfBirth = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        cut = new PregnancyDate(startDate,dayOfBirth);

        assertEquals(10l, cut.getDaysToBirth());

    }
}