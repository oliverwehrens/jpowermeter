package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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

    @RequestMapping(value = "/power/w", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String getPowerNowWText() {
        return readingBuffer.getSmartMeterReading().powerNowText();
    }

    @RequestMapping(value = "/meter/wh", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String getMeterTotalKwhText() {
        return readingBuffer.getSmartMeterReading().meterTotalText();
    }
}
