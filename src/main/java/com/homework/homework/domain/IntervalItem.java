package com.homework.homework.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class IntervalItem {
    private LocalDateTime from;
    private LocalDateTime to;
    private BigDecimal average;

    public IntervalItem(final LocalDateTime from, final LocalDateTime to, final BigDecimal average) {
        this.from = from;
        this.to = to;
        this.average = average;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(final LocalDateTime from) {
        this.from = from;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(final LocalDateTime to) {
        this.to = to;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(final BigDecimal average) {
        this.average = average;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final IntervalItem that = (IntervalItem) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "IntervalItem{" +
                "from=" + from +
                ", to=" + to +
                ", average=" + average +
                '}';
    }
}
