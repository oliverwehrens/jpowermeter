package com.maxheapsize.jpm;

import com.maxheapsize.jpm.reader.EhzSmlReader;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
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

    private static DefaultGaugeService gaugeService;
    @Value(value = "${device:/dev/ttyUSB0}")
    public String device;
    @Autowired
    private EhzSmlReader ehzSmlReader;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        gaugeService = context.getBean(DefaultGaugeService.class);
    }

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void readPowerMeter() throws PortInUseException, IOException, UnsupportedCommOperationException {
        PowerMeterReading reading = ehzSmlReader.read(device);
        gaugeService.submit("consumptionOne", reading.consumptionOne.value.doubleValue());
        gaugeService.submit("consumptionTwo", reading.consumptionTwo.value.doubleValue());
        gaugeService.submit("consumptionNow", reading.consumptionNow.value.doubleValue());
        gaugeService.submit("consumptionTotal", reading.consumptionTotal.value.doubleValue());
    }

}
