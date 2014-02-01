package com.maxheapsize.jpm.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class TableConfig {

    JdbcTemplate createTables(String dbUrl) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUsername("sa");
        dataSource.setUrl(dbUrl);
        dataSource.setPassword("");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS reading(time TIMESTAMP, total_c long, one long, two long, now long, counter long)");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS jpm(key varchar(255), value varchar(255))");
        return jdbcTemplate;
    }
}
