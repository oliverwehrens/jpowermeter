package com.maxheapsize.jpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PowerMeterRepository {

    @Autowired
    private JdbcTemplate dataStore;

    public void persist(PowerMeterReading reading) {
        Timestamp timestamp = new Timestamp(reading.date.getTime());
        dataStore.update("INSERT INTO reading(time , total_c, one, two, now, counter) values(?,?,?,?,?,?)",
                timestamp, reading.consumptionTotal.value, reading.consumptionOne.value, reading.consumptionTwo.value, reading.consumptionNow.value, "1");
    }

    public List<PowerMeterReading> getTimeSortedReadings() {
        List<PowerMeterReading> results = new ArrayList<>();
        List<Map<String, Object>> rows = dataStore.queryForList("SELECT * from reading");
        for (Map row : rows) {
            PowerMeterReading reading = new PowerMeterReading();
            reading.date = new Date(((Timestamp)row.get("time")).getTime());
            reading.consumptionTotal = new Consumption(((Long)row.get("total_c")), "WH");
            reading.consumptionOne = new Consumption(((Long)row.get("one")), "WH");
            reading.consumptionTwo = new Consumption(((Long)row.get("two")), "WH");
            reading.consumptionNow = new Consumption(((Long)row.get("now")), "W");
            results.add(reading);
        }
        return results;
    }

}
