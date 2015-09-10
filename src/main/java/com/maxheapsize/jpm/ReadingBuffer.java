package com.maxheapsize.jpm;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ReadingBuffer {
    private SmartMeterReading smartMeterReading;

    public SmartMeterReading getSmartMeterReading() {
        return smartMeterReading;
    }

    public void setSmartMeterReading(SmartMeterReading smartMeterReading) {
        this.smartMeterReading = smartMeterReading;
    }

    public SmartMeterReading getSmartMeterReadingInKwh() {
        SmartMeterReading smartMeterReadingInKwh = new SmartMeterReading();
        smartMeterReadingInKwh.consumptionTotal.value = smartMeterReading.consumptionTotal.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.consumptionTotal.unit = "kWh";
        smartMeterReadingInKwh.consumptionOne.value = smartMeterReading.consumptionOne.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.consumptionOne.unit = "kWh";
        smartMeterReadingInKwh.consumptionTwo.value = smartMeterReading.consumptionTotal.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.consumptionTwo.unit = "kWh";
        smartMeterReadingInKwh.consumptionNow.value = smartMeterReading.consumptionNow.value;
        smartMeterReadingInKwh.consumptionNow.unit = smartMeterReading.consumptionNow.unit;
        return smartMeterReadingInKwh;
    }

}
