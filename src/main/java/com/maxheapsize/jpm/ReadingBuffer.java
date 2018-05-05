package com.maxheapsize.jpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ReadingBuffer {
    private final static Logger log = LoggerFactory.getLogger(ReadingBuffer.class);
    private SmartMeterReading currentSmartMeterReading = new SmartMeterReading();
    private Map<String, SmartMeterReading> readings = new HashMap<>();

    Set<String> getDevices() {
        return readings.keySet();
    }

    SmartMeterReading getSmartMeterReading(String device) {
        if (readings.containsKey(device)) {
            log.debug("Getting new Reading {}.", currentSmartMeterReading);
            return readings.get(device);
        } else {
            throw new DeviceNotFoundException("Could not find device.", device);
        }
    }

    void setSmartMeterReading(String device, SmartMeterReading newSmartMeterReading) {
        readings.put(device, newSmartMeterReading);
        log.debug("Setting new Reading for {} in Buffer {}.", device, newSmartMeterReading);
    }

}
