package com.homework.homework.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homework.homework.domain.DataPoint;
import com.homework.homework.domain.IntervalItem;
import com.homework.homework.repository.DataPointRepository;

@Service
public class StatisticsService {

	private DataPointRepository dataPointRepository;

	@Autowired
	public StatisticsService(final DataPointRepository dataPointRepository) {
		this.dataPointRepository = dataPointRepository;
	}

	public List<IntervalItem> calculateAverages(final String entity) {
		final List<DataPoint> entities = dataPointRepository.findDevices(entity);
		if (entities.isEmpty()) {
			return Collections.emptyList();
		}
		entities.sort(Comparator.comparing(DataPoint::getTimestamp));

		final LocalDateTime fromTimestamp = entities.get(0).getTimestamp();
		final LocalDateTime toTimestamp = LocalDateTime.now();

		final List<IntervalItem> intervalItems = generateIntervals(fromTimestamp, toTimestamp, 15);

		intervalItems.forEach(intervalItem -> {
			final List<DataPoint> intervalDataPoints = entities.stream()
					.filter(
							dp -> dp.getTimestamp().isBefore(intervalItem.getTo())
									&& (dp.getTimestamp().isAfter(intervalItem.getFrom())
									|| (dp.getTimestamp().isEqual(intervalItem.getFrom()))
							)).collect(Collectors.toList());
			intervalItem.setAverage(calculateAverage(intervalDataPoints));
		});

		return intervalItems;
	}

	public List<IntervalItem> calculateMovingAverages(final int windowSize, final String entity) {
		final List<IntervalItem> aggregatedAverages = calculateAverages(entity);

		Queue<BigDecimal> summingQueue = new ArrayBlockingQueue<>(windowSize);
		final List<BigDecimal> movingAverageResults = aggregatedAverages.stream()
				.map(element -> {
					summingQueue.add(element.getAverage());
					BigDecimal movingAvg = summingQueue.stream()
							.reduce(BigDecimal.ZERO, BigDecimal::add)
							.divide(new BigDecimal(summingQueue.size()), RoundingMode.HALF_UP);
					if (summingQueue.size() < windowSize) {
						return null;
					}
					if (summingQueue.size() == windowSize) {
						summingQueue.remove();
					}
					return movingAvg;
				})
				.collect(Collectors.toList());

		final List<IntervalItem> result = new ArrayList<>(movingAverageResults.size());

		for (int i = windowSize - 1; i < movingAverageResults.size(); i++) {
			final BigDecimal movingAvg = movingAverageResults.get(i);
			LocalDateTime from = aggregatedAverages.get(i - windowSize + 1).getFrom();
			LocalDateTime to = aggregatedAverages.get(i).getTo();
			IntervalItem intervalItem = new IntervalItem(from, to, movingAvg);
			result.add(intervalItem);
		}

		return result;
	}

	private BigDecimal calculateAverage(final List<DataPoint> intervalDataPoints) {
		if (intervalDataPoints.isEmpty()) {
			return BigDecimal.ZERO;
		}
		final BigDecimal sum = intervalDataPoints.stream()
				.map(DataPoint::getValue)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return sum.divide(BigDecimal.valueOf(intervalDataPoints.size()), RoundingMode.HALF_UP);
	}

	private List<IntervalItem> generateIntervals(final LocalDateTime fromTimestamp, final LocalDateTime toTimestamp, final int intervalMinutes) {
		final List<IntervalItem> intervalItems = new LinkedList<>();
		LocalDateTime intervalFrom = fromTimestamp.withSecond(0).withNano(0);
		while (intervalFrom.isBefore(toTimestamp)) {
			intervalItems.add(new IntervalItem(intervalFrom, intervalFrom.plusMinutes(intervalMinutes), null));
			intervalFrom = intervalFrom.plusMinutes(intervalMinutes);
		}

		return intervalItems;
	}
}
