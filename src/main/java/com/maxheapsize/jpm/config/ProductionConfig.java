package com.maxheapsize.jpm.config;

import com.maxheapsize.jpm.reader.EhzSmlReader;
import com.maxheapsize.jpm.reader.DeviceEhzSmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;

@Profile("production")
@Configuration
public class ProductionConfig {

    @Bean
    public EhzSmlReader ehzSmlReader() {
        return new DeviceEhzSmlReader();
    }

}
