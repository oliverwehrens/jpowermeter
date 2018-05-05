package com.maxheapsize.jpm;

import com.maxheapsize.jpm.reader.DeviceEhzSmlReader;
import com.maxheapsize.jpm.reader.EhzSmlReader;
import com.maxheapsize.jpm.reader.SimulatedEhzSmlReader;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);
    @Value(value = "${devices:ttyUSB0}")
    public String devices;
    private EhzSmlReader ehzSmlReader;
    @Autowired
    private DeviceEhzSmlReader deviceEhzSmlReader;
    @Autowired
    private SimulatedEhzSmlReader simulatedEhzSmlReader;
    @Autowired
    private ReadingBuffer readingBuffer;
    private List<String> deviceList = new ArrayList<>();
    @Autowired
    private DeviceNameSplitter deviceNameSplitter;

    @PostConstruct
    public void setupReader() {
        deviceList = deviceNameSplitter.split(devices);
        ehzSmlReader = getReader(deviceList.get(0));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
    public void readPowerMeter() throws PortInUseException, IOException, UnsupportedCommOperationException {
        for (String device : deviceList) {
            SmartMeterReading reading = ehzSmlReader.read(device);
            if (reading.complete) {
                readingBuffer.setSmartMeterReading(device, reading);
            }
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
