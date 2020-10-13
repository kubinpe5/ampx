package com.homework.homework.repository;

import com.homework.homework.domain.DataPoint;
import com.homework.homework.exception.NotUniqueDataException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class DataPointRepository {
    private final Map<String, DataPoint> data = new ConcurrentHashMap<>();

    public void addDataPoint(final DataPoint dataPoint) {
        if (data.putIfAbsent(dataPoint.computeId(), dataPoint) != null) {
            throw new NotUniqueDataException();
        }
    }

    public synchronized void deleteDevices(final String device) {
        final Set<String> deviceIdsToRemove = data.entrySet().stream()
                .filter(dp -> dp.getValue().getDevice().equals(device))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        for (final String id : deviceIdsToRemove) {
            data.remove(id);
        }
    }

    public synchronized void deleteUsers(final String user) {
        final Set<String> usersIdsToRemove = data.entrySet().stream()
                .filter(dp -> dp.getValue().getUser().equals(user))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        for (final String id : usersIdsToRemove) {
            data.remove(id);
        }
    }

    public boolean contains(final DataPoint dataPoint) {
        return data.containsKey(dataPoint.computeId());
    }

    public List<DataPoint> findDevices(final String device) {
        return data.values().stream()
                .filter(dataPoint -> dataPoint.getDevice().equals(device))
                .collect(Collectors.toList());
    }
}
