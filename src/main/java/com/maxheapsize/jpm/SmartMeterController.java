package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class SmartMeterController {

    @Autowired
    ReadingBuffer readingBuffer = new ReadingBuffer();

    @RequestMapping(value ="/", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public ServiceDocument getDevices() {
        return new ServiceDocument(readingBuffer.getDevices());
    }


    @RequestMapping(value = "/{device}", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public SmartMeterReading getConsumptionNowJson(@PathVariable String device) {
        return readingBuffer.getSmartMeterReading(device);
    }

    @RequestMapping(value = "/{device}/kwh", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public SmartMeterReading getConsumptionNowJsonKwh(@PathVariable String device) {
        return readingBuffer.getSmartMeterReading(device).inKwh();
    }

    @RequestMapping(value = "/{device}/power/w", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String getPowerNowWText(@PathVariable String device) {
        return readingBuffer.getSmartMeterReading(device).powerNowText();
    }

    @RequestMapping(value = "/{device}/meter/wh", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String getMeterTotalKwhText(@PathVariable String device) {
        return readingBuffer.getSmartMeterReading(device).meterTotalText();
    }
}
