package com.homework.homework.controller;

import com.homework.homework.service.DataPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DevicesController {

    private DataPointService dataPointService;

    @Autowired
    public DevicesController(final DataPointService dataPointService) {
        this.dataPointService = dataPointService;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/devices/{device}/data-points")
    public ResponseEntity<Void> deleteDataPointForDevice(@PathVariable String device) {
        dataPointService.deleteDataPointsForDevice(device);
        return ResponseEntity.ok().build();
    }
}
