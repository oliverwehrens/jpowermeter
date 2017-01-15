package com.maxheapsize.jpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReadingBuffer {
    final static Logger log = LoggerFactory.getLogger(ReadingBuffer.class);
    private SmartMeterReading currentSmartMeterReading = new SmartMeterReading();

    public SmartMeterReading getSmartMeterReading() {
        log.debug("Getting new Reading {}.", currentSmartMeterReading);
        return currentSmartMeterReading;
    }

    public void
    setSmartMeterReading(SmartMeterReading newSmartMeterReading) {
        this.currentSmartMeterReading = newSmartMeterReading;
        log.debug("Setting new Reading in Buffer {}.", newSmartMeterReading);
    }

}
