package br.com.hcbtechsolution.booking.domain.vos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateRangeVoTest {

    @Test
    @DisplayName("Should throw exception when end date is before start date")
    void shouldThrowException_whenEndDateIsBeforeStartDate() {
        var startDate = LocalDateTime.of(2025, 1, 10, 0, 0);
        var endDate = LocalDateTime.of(2025, 1, 8, 0, 0);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new DateRangeVo(startDate, endDate));

        String expectedMessage = "End date must be after start date";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Should create date range when end date is after start date and check results")
    void shouldCreateDateRange_whenEndDateIsAfterStartDate() {
        var startDate = LocalDateTime.of(2025, 1, 10, 0, 0);
        var endDate = LocalDateTime.of(2025, 1, 13, 0, 0);

        DateRangeVo dateRange = new DateRangeVo(startDate, endDate);

        assertEquals(startDate, dateRange.getStartDate());
        assertEquals(endDate, dateRange.getEndDate());
    }

    @Test
    @DisplayName("Should Calculate Total Nights when Date Range id Created")
    void shouldCalculateTotalNights_whenDateRangeIsCreated() {
        var startDate = LocalDateTime.of(2025, 1, 10, 0, 0);
        var endDate = LocalDateTime.of(2025, 1, 13, 0, 0);
        DateRangeVo dateRange = new DateRangeVo(startDate, endDate);
        var totalNights = dateRange.getTotalNights();

        assertEquals(3, totalNights);

        var startDate2 = LocalDateTime.of(2025, 1, 10, 14, 0);
        var endDate2 = LocalDateTime.of(2025, 1, 16, 20, 0);
        DateRangeVo dateRange2 = new DateRangeVo(startDate2, endDate2);
        var totalNights2 = dateRange2.getTotalNights();

        assertEquals(7, totalNights2);
    }

    @Test
    @DisplayName("Should Check for Date Range Overlap when 2 Date Ranges Overlap")
    void shouldCheckForDateRangeOverlap_when2DateRangesOverlap() {
        var startDate = LocalDateTime.of(2025, 1, 10, 0, 0);
        var endDate = LocalDateTime.of(2025, 1, 13, 0, 0);
        DateRangeVo dateRange = new DateRangeVo(startDate, endDate);

        var startDate2 = LocalDateTime.of(2025, 1, 12, 0, 0);
        var endDate2 = LocalDateTime.of(2025, 1, 16, 0, 0);
        DateRangeVo dateRange2 = new DateRangeVo(startDate2, endDate2);

        var startDate3 = LocalDateTime.of(2025, 1, 10, 0, 0);
        var endDate3 = LocalDateTime.of(2025, 1, 16, 0, 0);
        DateRangeVo dateRange3 = new DateRangeVo(startDate3, endDate3);

        var overlaps = dateRange2.overlaps(dateRange);
        assertEquals(true, overlaps);

        var overlaps2 = dateRange3.overlaps(dateRange);
        assertEquals(true, overlaps2);

        var overlaps3 = dateRange3.overlaps(dateRange2);
        assertEquals(true, overlaps3);
    }

    @Test
    @DisplayName("Should Throw Exception when start date and end date are the same")
    void shouldThrowException_whenStartDateAndEndDateAreTheSame() {
        var date = LocalDateTime.of(2025, 1, 10, 0, 0);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new DateRangeVo(date, date));

        String expectedMessage = "Start date and end date cannot be the same";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
