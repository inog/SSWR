package de.ingoreschke.sswr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class WidgetTest {

    private Widget widget;
    private SharedPreferences mockPrefs;
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUp() {
        widget = new Widget();
        mockPrefs = mock(SharedPreferences.class);
        mockEditor = mock(SharedPreferences.Editor.class);
        when(mockPrefs.edit()).thenReturn(mockEditor);
        widget.et = mockPrefs;
    }

    @Test
    public void testCalculateSswDate_ZeroYear() {
        PregnancyDate result = widget.calculateSswDate(0, 5, 12);
        assertNull(result);
    }

    @Test
    public void testCalculateSswDate_ZeroDay() {
        PregnancyDate result = widget.calculateSswDate(2026, 5, 0);
        assertNull(result);
    }

    @Test
    public void testCalculateSswDate_ValidDate() {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(100);
        
        PregnancyDate result = widget.calculateSswDate(dueDate.getYear(), dueDate.getMonthValue() - 1, dueDate.getDayOfMonth());
        assertNotNull(result);
        assertEquals(100, result.getDaysToBirth());
    }

    @Test
    public void testCalculateSswDate_InvalidDate_ClearsPrefs() {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(500);

        PregnancyDate result = widget.calculateSswDate(dueDate.getYear(), dueDate.getMonthValue() - 1, dueDate.getDayOfMonth());
        assertNull(result);
        verify(mockPrefs).edit();
        verify(mockEditor).clear();
        verify(mockEditor).commit();
    }
    
    @Test
    public void testCalculateSswDate_DateTimeException_ClearsPrefs() {
        PregnancyDate result = widget.calculateSswDate(2026, 1, 30); // month=1 => Feb 30th (invalid date)
        assertNull(result);
        verify(mockPrefs).edit();
        verify(mockEditor).clear();
        verify(mockEditor).commit();
    }
}
