package com.maxheapsize.jpm;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

    @Value(value = "${device:/dev/ttyUSB0}")
    public String device;
    @Autowired
    private PowerMeterValueService service;
    @Autowired
    private EhzSmlReader ehzSmlReader;
    @Autowired
    private PowerMeterRepository powerMeterRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() throws PortInUseException, IOException, UnsupportedCommOperationException {
        PowerMeterReading reading = ehzSmlReader.read(device);
        service.setPowerMeterReading(reading);
        powerMeterRepository.persist(reading);
    }

}
