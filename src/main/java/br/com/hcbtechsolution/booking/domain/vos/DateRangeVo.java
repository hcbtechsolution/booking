package br.com.hcbtechsolution.booking.domain.vos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateRangeVo {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DateRangeVo(LocalDateTime startDate, LocalDateTime endDate) {
        this.validate(startDate, endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (endDate.isEqual(startDate)) {
            throw new IllegalArgumentException("Start date and end date cannot be the same");
        }
    }

    public long getTotalNights() {
        double diffTime = ChronoUnit.SECONDS.between(this.startDate, this.endDate);
        return (long) Math.ceil(diffTime / (60 * 60 * 24));
    }

    public boolean overlaps(DateRangeVo otherDateRange) {
        return (otherDateRange.getStartDate().isAfter(startDate) && otherDateRange.getStartDate().isBefore(endDate))
                || (otherDateRange.getEndDate().isAfter(startDate) && otherDateRange.getEndDate().isBefore(endDate));
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "DateRangeVo [startDate=" + startDate + ", endDate=" + endDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRangeVo other = (DateRangeVo) obj;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        return true;
    }
}
