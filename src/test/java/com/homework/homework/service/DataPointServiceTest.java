package com.homework.homework.service;

import com.homework.homework.domain.DataPoint;
import com.homework.homework.exception.NotUniqueDataException;
import com.homework.homework.repository.DataPointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.xml.crypto.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DataPointServiceTest {

    private DataPointRepository repository;

    private DataPointService dataPointService;

    @BeforeEach
    void setUp() {
        repository = new DataPointRepository();
        dataPointService = new DataPointService(repository);
        final DataPoint dataPoint = new DataPoint(LocalDateTime.parse("2015-03-26T10:58:51"), BigDecimal.valueOf(10), "device", "user");
        repository.addDataPoint(dataPoint);
    }

    @Test
    void addDataPointToSeries() {
        final DataPoint dataPoint = new DataPoint(LocalDateTime.parse("2015-04-26T10:58:51"), BigDecimal.valueOf(10), "device", "user");
        dataPointService.addDataPointToSeries(dataPoint);
        assertTrue(repository.contains(dataPoint));
    }

    @Test
    void addDataPointToSeriesIfExists() {
        final DataPoint dataPoint = new DataPoint(LocalDateTime.parse("2015-03-26T10:58:51"), BigDecimal.valueOf(10), "device", "user");
        assertThrows(NotUniqueDataException.class, () -> dataPointService.addDataPointToSeries(dataPoint));
    }

    @Test
    void deleteDataPointsForDevice() {
        final DataPoint dataPoint = new DataPoint(LocalDateTime.parse("2015-03-26T10:58:51"), BigDecimal.valueOf(10), "device", "user");
        assertTrue(repository.contains(dataPoint));
        dataPointService.deleteDataPointsForDevice("device");
        assertFalse(repository.contains(dataPoint));
    }

    @Test
    void deleteDataPointsForUser() {
        final DataPoint dataPoint = new DataPoint(LocalDateTime.parse("2015-03-26T10:58:51"), BigDecimal.valueOf(10), "device", "user");
        assertTrue(repository.contains(dataPoint));
        dataPointService.deleteDataPointsForUser("user");
        assertFalse(repository.contains(dataPoint));
    }
}