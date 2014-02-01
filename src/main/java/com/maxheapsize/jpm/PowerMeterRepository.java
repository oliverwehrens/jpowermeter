package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class PowerMeterRepository {

    @Autowired
    private JdbcTemplate dataStore;

    public void persist(PowerMeterReading reading) {
        Timestamp timestamp = new Timestamp(reading.date.getTime());
        dataStore.update("INSERT INTO reading(time , total_c, one, two, now, counter) values(?,?,?,?,?,?)",
                timestamp, reading.consumptionTotal.value, reading.consumptionOne.value, reading.consumptionTwo.value, reading.consumptionNow.value, "1");

    }

}
