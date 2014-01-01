package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pm")
public class PowerMeterController {

    @Autowired
    private PowerMeterValueService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    PowerMeterReading measure() {
        return service.getPowerMeterReading();
    }

}
