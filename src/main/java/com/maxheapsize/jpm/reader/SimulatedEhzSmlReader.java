package com.maxheapsize.jpm.reader;

import com.maxheapsize.jpm.Consumption;
import com.maxheapsize.jpm.SmartMeterReadout;
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
    public SmartMeterReadout read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {

        BigDecimal READING_NOW = new BigDecimal(new Random().nextInt(5000));
        READING_TOTAL = READING_TOTAL.add(READING_NOW.divide(new BigDecimal(WATT_TO_WATTHOURS), 2, BigDecimal.ROUND_DOWN));
        READING_ONE = READING_TOTAL;

        SmartMeterReadout smartMeterReadout = new SmartMeterReadout();
        smartMeterReadout.consumptionTotal = new Consumption(READING_TOTAL, "WH");
        smartMeterReadout.consumptionOne = new Consumption(READING_ONE, "WH");
        smartMeterReadout.consumptionTwo = new Consumption(READING_TWO, "WH");
        smartMeterReadout.consumptionNow = new Consumption(READING_NOW, "W");
        log.info(smartMeterReadout.toString());

        return smartMeterReadout;
    }

}
