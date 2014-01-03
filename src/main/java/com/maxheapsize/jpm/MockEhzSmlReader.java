package com.maxheapsize.jpm;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

@Service
public class MockEhzSmlReader implements EhzSmlReader {

    private long START_COUNTER = 121413280;

    public PowerMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {

        START_COUNTER = START_COUNTER + new Random().nextInt(30);
        PowerMeterReading powerMeterReading = new PowerMeterReading();
        powerMeterReading.consumptionTotal = new Consumption(START_COUNTER, "WH");
        return powerMeterReading;
    }

}
