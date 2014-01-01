package com.maxheapsize.jpm;

import org.springframework.stereotype.Service;

@Service
public class PowerMeterValueService {

    private PowerMeterReading powerMeterReading;

    public PowerMeterReading getPowerMeterReading() {
        return powerMeterReading;
    }

    public void setPowerMeterReading(PowerMeterReading powerMeterReading) {
        this.powerMeterReading = powerMeterReading;
    }
}
