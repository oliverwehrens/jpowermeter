package com.maxheapsize.jpm;

import org.springframework.stereotype.Service;

@Service
public class PowerMeterValueService {

    private PowerMeterReading powerMeterReading;
    private PowerMeterReading firstReadingOfTheDay = new PowerMeterReading();

    public PowerMeterReading getPowerMeterReading() {
        return powerMeterReading;
    }

    public void setPowerMeterReading(PowerMeterReading powerMeterReading) {
        this.powerMeterReading = powerMeterReading;
//        Instant instant = Instant.ofEpochMilli(powerMeterReading.date.getTime());
//        LocalDateTime readingDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
//
    }

}
