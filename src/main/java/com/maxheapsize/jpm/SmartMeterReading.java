package com.maxheapsize.jpm;

import java.math.BigDecimal;
import java.util.Date;

public class SmartMeterReading {

    public Date date = new Date();
    public Meter meterTotal = new Meter();
    public Meter meterOne = new Meter();
    public Meter meterTwo = new Meter();
    public Meter power = new Meter();
    public boolean complete = false;

    @Override
    public String toString() {
        return "SmartMeterReading{" +
                "date=" + date +
                ", meterTotal=" + meterTotal +
                ", meterOne=" + meterOne +
                ", meterTwo=" + meterTwo +
                ", power=" + power +
                ", complete=" + complete +
                '}';
    }

    public SmartMeterReading inKwh() {
        SmartMeterReading smartMeterReadingInKwh = new SmartMeterReading();
        smartMeterReadingInKwh.meterTotal.value = meterTotal.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.meterTotal.unit = "kWh";
        smartMeterReadingInKwh.meterOne.value = meterOne.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.meterOne.unit = "kWh";
        smartMeterReadingInKwh.meterTwo.value = meterTwo.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.meterTwo.unit = "kWh";
        smartMeterReadingInKwh.power.value = power.value;
        smartMeterReadingInKwh.power.unit = power.unit;
        smartMeterReadingInKwh.complete = complete;
        return smartMeterReadingInKwh;
    }

    public String powerNowText() {
        return power.value.toString();
    }

    public String meterTotalText() {
        return meterTotal.value.toString();
    }

}
