package com.homework.homework.controller;

import java.util.List;

import com.homework.homework.domain.IntervalItem;
import com.homework.homework.service.StatisticsService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/statistics/devices/{device}/avg")
    public ResponseEntity<List<IntervalItem>> deviceAverages(@PathVariable String device) {
        return ResponseEntity.ok().body(statisticsService.calculateAverages(device));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/statistics/devices/{device}/moving_avg")
    public ResponseEntity<List<IntervalItem>> deviceMovingAverages(@PathVariable String device, @RequestParam("window_size") int windowSize) {
        return ResponseEntity.ok().body(statisticsService.calculateMovingAverages(windowSize, device));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/statistics/users/{user}/avg")
    public ResponseEntity<List<IntervalItem>> userAverages(@PathVariable String user) {
        return ResponseEntity.ok().body(statisticsService.calculateAverages(user));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/statistics/users/{user}/moving_avg")
    public ResponseEntity<List<IntervalItem>> userMovingAverages(@PathVariable String user, @RequestParam("window_size") int windowSize) {
        return ResponseEntity.ok().body(statisticsService.calculateMovingAverages(windowSize, user));
    }
}
