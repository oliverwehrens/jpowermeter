package com.maxheapsize.jpm.config;

import com.maxheapsize.jpm.reader.DeviceEhzSmlReader;
import com.maxheapsize.jpm.reader.EhzSmlReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("production")
@Configuration
public class ProductionConfig {

    @Bean
    public EhzSmlReader ehzSmlReader() {
        return new DeviceEhzSmlReader();
    }

}
