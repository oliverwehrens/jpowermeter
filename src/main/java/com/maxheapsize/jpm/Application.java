package com.maxheapsize.jpm;

import com.maxheapsize.jpm.reader.EhzSmlReader;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
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

    @Value(value = "${device:/dev/ttyUSB0}")
    public String device;
    @Autowired
    private PowerMeterValueService service;
    @Autowired
    private EhzSmlReader ehzSmlReader;
    private static PowerMeterReadingRepository powerMeterReadingRepository;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        powerMeterReadingRepository = context.getBean(PowerMeterReadingRepository.class);
    }

    @Scheduled(fixedRate = 5000)
    public void readPowerMeter() throws PortInUseException, IOException, UnsupportedCommOperationException {
        PowerMeterReading reading = ehzSmlReader.read(device);
        service.setPowerMeterReading(reading);
        powerMeterReadingRepository.save(reading);
    }

}
