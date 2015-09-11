package com.maxheapsize.jpm;

import com.maxheapsize.jpm.reader.DeviceEhzSmlReader;
import com.maxheapsize.jpm.reader.EhzSmlReader;
import com.maxheapsize.jpm.reader.SimulatedEhzSmlReader;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.metrics.writer.DefaultCounterService;
import org.springframework.boot.actuate.metrics.writer.DefaultGaugeService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class Application {

    final static Logger log = LoggerFactory.getLogger(Application.class);
    private static DefaultGaugeService gaugeService;
    private static DefaultCounterService counterService;
    @Value(value = "${device:/dev/ttyUSB0}")
    public String device;
    private EhzSmlReader ehzSmlReader;
    @Autowired
    private DeviceEhzSmlReader deviceEhzSmlReader;
    @Autowired
    private SimulatedEhzSmlReader simulatedEhzSmlReader;
    @Autowired
    private ReadingBuffer readingBuffer;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        gaugeService = context.getBean(DefaultGaugeService.class);
        counterService = context.getBean(DefaultCounterService.class);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void readPowerMeter() throws PortInUseException, IOException, UnsupportedCommOperationException {

        if (ehzSmlReader == null) {
            ehzSmlReader = getReader(device);
        }

        SmartMeterReading reading = ehzSmlReader.read(device);
        if (reading.complete) {
            gaugeService.submit("consumption.one", reading.consumptionOne.value.doubleValue());
            gaugeService.submit("consumption.two", reading.consumptionTwo.value.doubleValue());
            gaugeService.submit("consumption.now", reading.consumptionNow.value.doubleValue());
            gaugeService.submit("consumption.total", reading.consumptionTotal.value.doubleValue());
            gaugeService.submit("consumption.timestamp", reading.date.getTime());
            readingBuffer.setSmartMeterReading(reading);
        } else {
            counterService.increment("consumption.error.read");
        }
    }

    private EhzSmlReader getReader(String device) {
        EhzSmlReader ehzSmlReader = null;
        if (device.equalsIgnoreCase("SIMULATED")) {
            ehzSmlReader = simulatedEhzSmlReader;
            log.info("Using simulated values from powermeter.");
        } else {
            ehzSmlReader = deviceEhzSmlReader;
            log.info("Using values from device {}.", device);
        }
        return ehzSmlReader;
    }

}
