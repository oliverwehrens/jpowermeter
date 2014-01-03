package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PowerMeterController {

    @Autowired
    private PowerMeterValueService service;

    @RequestMapping(value = "/api", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    PowerMeterReading measure() {
        return service.getPowerMeterReading();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    String web() {
        return service.getPowerMeterReading().consumptionTotal.value.toString();
    }


}
