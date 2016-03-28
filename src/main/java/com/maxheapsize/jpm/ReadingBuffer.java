package com.maxheapsize.jpm;

import org.springframework.stereotype.Service;

@Service
public class ReadingBuffer {
  private SmartMeterReading currentSmartMeterReading = new SmartMeterReading();

  public SmartMeterReading getSmartMeterReading() {
    return currentSmartMeterReading;
  }

  public void setSmartMeterReading(SmartMeterReading newSmartMeterReading) {
    if (!currentSmartMeterReading.complete || newSmartMeterReading.powerWithin2PercentMeasuringInaccuracy(currentSmartMeterReading.power.value)) {
      this.currentSmartMeterReading = newSmartMeterReading;
    }
  }

}
