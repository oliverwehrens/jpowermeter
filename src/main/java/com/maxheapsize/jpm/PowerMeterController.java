package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class PowerMeterController {

    @Autowired
    private PowerMeterValueService service;

    @Autowired
    private PowerMeterReadingRepository powerMeterReadingRepository;

    @RequestMapping(value = "/today", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    long measureToday() {
        LocalDate now = LocalDate.now();
        List<PowerMeterReading> readings = getByDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        if (readings.isEmpty()) {
            return 0;
        }
        return readings.get(readings.size() - 1).consumptionTotal.value - readings.get(0).consumptionTotal.value;
    }
    @RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PowerMeterReading> all() {
        Iterable<PowerMeterReading> all = powerMeterReadingRepository.findAll();
        List<PowerMeterReading> result = new ArrayList();
        for (PowerMeterReading powerMeterReading : all) {
            result.add(powerMeterReading);
        }
        return result;

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public
    @ResponseBody
    ModelAndView web() {
        ModelAndView model = new ModelAndView();
        model.getModelMap().addAttribute("reading", service.getPowerMeterReading());
        model.setViewName("home");
        return model;
    }

    private List<PowerMeterReading> getByDate(int year, int month, int day) {
        LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, month, day, 23, 59, 59);

        Date startDate = Date.from(Instant.from(start.atZone(ZoneId.systemDefault())));
        Date endDate = Date.from(Instant.from(end.atZone(ZoneId.systemDefault())));

        List<PowerMeterReading> readings = powerMeterReadingRepository.findDateInRange(startDate, endDate);
        return readings;
    }

}
