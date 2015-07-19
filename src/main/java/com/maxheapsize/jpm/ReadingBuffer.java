package com.maxheapsize.jpm;

import org.springframework.stereotype.Service;

@Service
public class ReadingBuffer {
    private SmartMeterReadout smartMeterReadout;

    public SmartMeterReadout getSmartMeterReadout() {
        return smartMeterReadout;
    }

    public void setSmartMeterReadout(SmartMeterReadout smartMeterReadout) {
        this.smartMeterReadout = smartMeterReadout;
    }

}
