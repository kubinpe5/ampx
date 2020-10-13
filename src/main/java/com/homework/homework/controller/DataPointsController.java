package com.homework.homework.controller;

import com.homework.homework.domain.DataPoint;
import com.homework.homework.exception.NotUniqueDataException;
import com.homework.homework.service.DataPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DataPointsController {

    private DataPointService dataPointService;

    @Autowired
    public DataPointsController(final DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/data-points")
    public ResponseEntity<Void> addDataPointToSeries(@RequestBody DataPoint dataPoint) {
        try {
            dataPointService.addDataPointToSeries(dataPoint);
        } catch (NotUniqueDataException e) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
