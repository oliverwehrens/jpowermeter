package com.maxheapsize.jpm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    public EhzSmlReader ehzSmlReader() {
        return new MockEhzSmlReader();
    }

    @Bean
    public JdbcTemplate dataStore() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl("jdbc:h2:file:/tmp/a");
        dataSource.setPassword("");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS reading(time TIMESTAMP, total_c long, one long, two long, now long, counter_id varchar(10))");
        return jdbcTemplate;
    }

}
