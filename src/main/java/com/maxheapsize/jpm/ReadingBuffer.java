package com.maxheapsize.jpm;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ReadingBuffer {
    private SmartMeterReading currentSmartMeterReading = new SmartMeterReading();

    @Value(value = "${influxdburl}")
    public String influxdburl;

    @Value(value = "${influxdbuser}")
    public String influxdbuser;

    @Value(value = "${influxdbpw}")
    public String influxdbpassword;

    @Value(value = "${influxdbdatabase}")
    public String influxdbdatabase;

    public SmartMeterReading getSmartMeterReading() {
        return currentSmartMeterReading;
    }

    public void setSmartMeterReading(SmartMeterReading newSmartMeterReading) {

        if (newSmartMeterReading.powerWithin2PercentMeasuringInaccuracy(currentSmartMeterReading.power.value)) {
            if (influxdburl != null) {
                InfluxDB influxDB = InfluxDBFactory.connect(influxdburl, influxdbuser, influxdbpassword);
                Serie serie = new Serie.Builder(influxdbdatabase).columns("one", "two", "total", "now").values(
                        newSmartMeterReading.meterOne.value,
                        newSmartMeterReading.meterTwo.value,
                        newSmartMeterReading.meterTotal.value,
                        newSmartMeterReading.power.value).build();
                influxDB.write("jpm", TimeUnit.MILLISECONDS, serie);
            }
        }

        this.currentSmartMeterReading = newSmartMeterReading;
    }

}
