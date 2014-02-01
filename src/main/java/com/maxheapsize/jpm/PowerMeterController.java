package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
        List<PowerMeterReading> readings = repository.getReadings();

        for (PowerMeterReading reading : readings) {
            System.out.println(reading);
        }


        model.setViewName("home");
        return model;
    }


}
