package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartMeterController {

    @Autowired
    private ReadingBuffer readingBuffer;

    @RequestMapping(value = "/reading", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody SmartMeterReadout getConsumptionNowJson() {
        return readingBuffer.getSmartMeterReadout();
    }
}
