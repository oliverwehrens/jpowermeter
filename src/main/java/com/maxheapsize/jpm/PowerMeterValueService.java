package com.maxheapsize.jpm;

import org.springframework.stereotype.Service;

@Service
public class PowerMeterValueService {

    private PowerMeter powerMeter;

    public PowerMeter getPowerMeter() {
        return powerMeter;
    }

    public void setPowerMeter(PowerMeter powerMeter) {
        this.powerMeter = powerMeter;
    }
}
