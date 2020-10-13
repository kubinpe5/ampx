package com.homework.homework.service;

import com.homework.homework.domain.DataPoint;
import com.homework.homework.repository.DataPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataPointService {

    private DataPointRepository repository;

    @Autowired
    public DataPointService(final DataPointRepository repository) {
        this.repository = repository;
    }

    public void addDataPointToSeries(final DataPoint dataPoint) {
        repository.addDataPoint(dataPoint);
    }

    public void deleteDataPointsForDevice(final String device) {
        repository.deleteDevices(device);
    }

    public void deleteDataPointsForUser(final String user) {
        repository.deleteUsers(user);
    }
}
