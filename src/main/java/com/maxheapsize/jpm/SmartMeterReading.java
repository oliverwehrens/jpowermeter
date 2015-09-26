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
        return complete ?
                "Reading from: " + date + " with values:" +
                        "\nTotal: " + meterTotal +
                        "\nOne: " + meterOne +
                        "\nTwo: " + meterTwo +
                        "\nNow: " + power
                :
                "Not recorded.";
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
        return smartMeterReadingInKwh;
    }

    public boolean powerWithin2PercentMeasuringInaccuracy(BigDecimal otherPower) {
        BigDecimal lowerBorder = otherPower.multiply(new BigDecimal(0.98));
        BigDecimal upperBorder = otherPower.multiply(new BigDecimal(1.02));
        int less = -1;
        int greater = 1;

        int upper = power.value.compareTo(upperBorder);
        int lower = power.value.compareTo(lowerBorder);

        if (upper == less && lower == greater) {
            return true;
        }
        return false;
    }
}
