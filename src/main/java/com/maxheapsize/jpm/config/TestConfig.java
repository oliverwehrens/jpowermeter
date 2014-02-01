package com.maxheapsize.jpm.config;

import com.maxheapsize.jpm.reader.EhzSmlReader;
import com.maxheapsize.jpm.reader.SimulatedEhzSmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Profile("test")
@Configuration
public class TestConfig {

    @Autowired
    TableConfig tableConfig;

    @Bean
    public EhzSmlReader ehzSmlReader() {
        return new SimulatedEhzSmlReader();
    }

    @Bean
    public JdbcTemplate dataStore() {
        return tableConfig.createTables("jdbc:h2:file:/tmp/a");
    }

}
