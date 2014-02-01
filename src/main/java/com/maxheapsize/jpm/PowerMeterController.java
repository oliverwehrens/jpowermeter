package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PowerMeterController {

    @Autowired
    private PowerMeterValueService service;

    @Autowired
    private PowerMeterRepository repository;

    @RequestMapping(value = "/api", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody PowerMeterReading measure() {
        return service.getPowerMeterReading();
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public @ResponseBody ModelAndView web() {
        ModelAndView model = new ModelAndView();
        model.getModelMap().addAttribute("reading", service.getPowerMeterReading());
        List<PowerMeterReading> readings = repository.getTimeSortedReadings();

        for (PowerMeterReading reading : readings) {
            System.out.println(reading);
        }


        model.setViewName("home");
        return model;
    }

    @RequestMapping(value="/chart", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ChartPoint> getChart() {
        List<PowerMeterReading> readings = repository.getTimeSortedReadings();
        Chart chart = new Chart();
        for (PowerMeterReading reading : readings) {
            chart.addReading(reading);
        }
        return chart.points;
    }


}
