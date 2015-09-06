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

    @RequestMapping(value = "/meterreader", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    SmartMeterReading getConsumptionNowJson() {
        return readingBuffer.getSmartMeterReading();
    }

    @RequestMapping(value = "/meterreader/kwh", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody
    SmartMeterReading getConsumptionNowJsonKwh()
    {
        return readingBuffer.getSmartMeterReadingInKwh();
    }

}
