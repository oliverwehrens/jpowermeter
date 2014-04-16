package com.maxheapsize.jpm.reader;

import com.maxheapsize.jpm.Consumption;
import com.maxheapsize.jpm.PowerMeterReading;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

@Service
public class SimulatedEhzSmlReader implements EhzSmlReader {

    private BigDecimal START_COUNTER = new BigDecimal(121413280);

    @Override
    public PowerMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {

        BigDecimal random = new BigDecimal(Math.random());
        BigDecimal random2 = new BigDecimal(Math.random());

        BigDecimal consumptionNow = random.divide(new BigDecimal(5000),BigDecimal.ROUND_DOWN);
        START_COUNTER = START_COUNTER.add(random2.divide(new BigDecimal(30), BigDecimal.ROUND_DOWN));

        PowerMeterReading powerMeterReading = new PowerMeterReading();
        powerMeterReading.consumptionTotal = new Consumption(START_COUNTER, "WH");
        powerMeterReading.consumptionNow = new Consumption(consumptionNow, "W");
        return powerMeterReading;
    }

}
