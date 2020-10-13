package com.homework.homework.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DataPoint {
    private static final String ID_DELIMITER = "_";

    private LocalDateTime timestamp;
    private BigDecimal value;
    private String device;
    private String user;

    public DataPoint() {}

    public DataPoint(final LocalDateTime timestamp, final BigDecimal value, final String device, final String user) {
        this.timestamp = timestamp;
        this.value = value;
        this.device = device;
        this.user = user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(final String device) {
        this.device = device;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String computeId() {
        return timestamp.toString() + ID_DELIMITER + device + ID_DELIMITER + user;
    }
}
