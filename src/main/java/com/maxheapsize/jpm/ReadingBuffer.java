package com.maxheapsize.jpm;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
public class ReadingBuffer {
    private SmartMeterReading smartMeterReading;

    @Value(value = "${influxdburl}")
    public String influxdburl;

    @Value(value = "${influxdbuser}")
    public String influxdbuser;

    @Value(value = "${influxdbpw}")
    public String influxdbpassword;

    @Value(value = "${influxdbdatabase}")
    public String influxdbdatabase;




    public SmartMeterReading getSmartMeterReading() {
        return smartMeterReading;
    }

    public void setSmartMeterReading(SmartMeterReading smartMeterReading) {
        this.smartMeterReading = smartMeterReading;

        if (influxdburl!=null) {
            InfluxDB influxDB = InfluxDBFactory.connect(influxdburl, influxdbuser, influxdbpassword);
            Serie serie = new Serie.Builder(influxdbdatabase).columns("one", "two", "total", "now").values(
                    smartMeterReading.meterOne.value,
                    smartMeterReading.meterTwo.value,
                    smartMeterReading.meterTotal.value,
                    smartMeterReading.power.value).build();
            influxDB.write("jpm", TimeUnit.MILLISECONDS, serie);
        }
    }

    public SmartMeterReading getSmartMeterReadingInKwh() {
        SmartMeterReading smartMeterReadingInKwh = new SmartMeterReading();
        smartMeterReadingInKwh.meterTotal.value = smartMeterReading.meterTotal.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.meterTotal.unit = "kWh";
        smartMeterReadingInKwh.meterOne.value = smartMeterReading.meterOne.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.meterOne.unit = "kWh";
        smartMeterReadingInKwh.meterTwo.value = smartMeterReading.meterTwo.value.divide(new BigDecimal(1000), BigDecimal.ROUND_DOWN);
        smartMeterReadingInKwh.meterTwo.unit = "kWh";
        smartMeterReadingInKwh.power.value = smartMeterReading.power.value;
        smartMeterReadingInKwh.power.unit = smartMeterReading.power.unit;
        return smartMeterReadingInKwh;
    }

}
