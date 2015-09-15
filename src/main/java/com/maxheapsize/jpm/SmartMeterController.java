package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartMeterController {

    @Autowired
     ReadingBuffer readingBuffer = new ReadingBuffer();

    @RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public SmartMeterReading getConsumptionNowJson() {
        return readingBuffer.getSmartMeterReading();
    }

    @RequestMapping(value = "/kwh", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public SmartMeterReading getConsumptionNowJsonKwh() {
        return readingBuffer.getSmartMeterReading().inKwh();
    }

}
