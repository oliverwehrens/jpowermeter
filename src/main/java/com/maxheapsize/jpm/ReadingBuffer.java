package com.maxheapsize.jpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReadingBuffer {
  private SmartMeterReading currentSmartMeterReading = new SmartMeterReading();

  final static Logger log = LoggerFactory.getLogger(ReadingBuffer.class);


  public SmartMeterReading getSmartMeterReading() {
    return currentSmartMeterReading;
  }

  public void setSmartMeterReading(SmartMeterReading newSmartMeterReading) {
    if (!currentSmartMeterReading.complete || newSmartMeterReading.powerWithin2PercentMeasuringInaccuracy(currentSmartMeterReading.power.value)) {
      this.currentSmartMeterReading = newSmartMeterReading;
    } else {
      log.debug("Not setting Buffer, within 2%. ", newSmartMeterReading);
    }
  }

}
