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

    @Autowired
    private PowerMeterValueService service;

    @Value(value = "${device:/dev/ttyUSB0}")
    public String device;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() throws PortInUseException, IOException, UnsupportedCommOperationException {
        EhzSmlReader ehzSmlReader = new EhzSmlReader();
        service.setPowerMeterReading(ehzSmlReader.read(device));
    }

}
