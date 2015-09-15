package com.maxheapsize.jpm.reader;

import com.maxheapsize.jpm.Meter;
import com.maxheapsize.jpm.SmartMeterReading;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

@Service
public class SimulatedEhzSmlReader implements EhzSmlReader {

    private final static Logger log = LoggerFactory.getLogger(SimulatedEhzSmlReader.class);
    private BigDecimal READING_TOTAL = new BigDecimal(0);
    private BigDecimal READING_ONE = new BigDecimal(0);
    private BigDecimal READING_TWO = new BigDecimal(0);
    int WATT_TO_WATTHOURS = 3600;

    @Override
    public SmartMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {

        BigDecimal READING_POWER = new BigDecimal(new Random().nextInt(5000));
        READING_TOTAL = READING_TOTAL.add(READING_POWER.divide(new BigDecimal(WATT_TO_WATTHOURS), 2, BigDecimal.ROUND_DOWN));
        READING_ONE = READING_TOTAL;

        SmartMeterReading smartMeterReading = new SmartMeterReading();
        smartMeterReading.meterTotal = new Meter(READING_TOTAL, "WH");
        smartMeterReading.meterOne = new Meter(READING_ONE, "WH");
        smartMeterReading.meterTwo = new Meter(READING_TWO, "WH");
        smartMeterReading.power = new Meter(READING_POWER, "W");
        log.debug(smartMeterReading.toString());

        return smartMeterReading;
    }

}
