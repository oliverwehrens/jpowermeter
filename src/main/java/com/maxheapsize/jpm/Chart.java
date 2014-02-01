package com.maxheapsize.jpm;

import java.util.ArrayList;

public class Chart {

    public ArrayList<ChartPoint> points = new ArrayList<>();
    private PowerMeterReading startPoint;

    public void addReading(PowerMeterReading powerMeterReading) {
        if (startPoint == null) {
            startPoint = powerMeterReading;
        } else {
            long value = powerMeterReading.consumptionTotal.value - startPoint.consumptionTotal.value;
            ChartPoint chartPoint = new ChartPoint();
            chartPoint.date = powerMeterReading.date.toString();
            chartPoint.wh = value;
            points.add(chartPoint);
        }
    }

}
