package com.maxheapsize.jpm;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


public class SmartMeterReadingTest {

    private SmartMeterReading smartMeterReading;

    @Before
    public void setup() {
        smartMeterReading = new SmartMeterReading();
    }

    @Test
    public void test102isWithInRange() throws Exception {
        smartMeterReading.power = new Meter(new BigDecimal(102), "kwh");
        assertThat(smartMeterReading.powerWithin2PercentMeasuringInaccuracy(new BigDecimal(100))).isTrue();
    }

    @Test
    public void test98isWithInRange() throws Exception {
        smartMeterReading.power = new Meter(new BigDecimal(98), "kwh");
        assertThat(smartMeterReading.powerWithin2PercentMeasuringInaccuracy(new BigDecimal(100))).isTrue();
    }

    @Test
    public void test100isWithInRange() throws Exception {
        smartMeterReading.power = new Meter(new BigDecimal(100), "kwh");
        assertThat(smartMeterReading.powerWithin2PercentMeasuringInaccuracy(new BigDecimal(100))).isTrue();
    }

    @Test
    public void test979isOutOfRange() throws Exception {
        smartMeterReading.power = new Meter(new BigDecimal(97.9), "kwh");
        assertThat(smartMeterReading.powerWithin2PercentMeasuringInaccuracy(new BigDecimal(100))).isFalse();
    }

    @Test
    public void test1021isOutOfRange() throws Exception {
        smartMeterReading.power = new Meter(new BigDecimal(102.1), "kwh");
        assertThat(smartMeterReading.powerWithin2PercentMeasuringInaccuracy(new BigDecimal(100))).isFalse();
    }

}