package com.maxheapsize.jpm;

import java.util.Date;

public class PowerMeterReading {

    public Date date = new Date();
    public Consumption consumptionTotal = new Consumption();
    public Consumption consumptionOne = new Consumption();
    public Consumption consumptionTwo = new Consumption();
    public Consumption consumptionNow = new Consumption();

    @Override
    public String toString() {
        return "Reading from: " + date + " with values:" +
                "\nTotal: " + consumptionTotal +
                "\nOne: " + consumptionOne +
                "\nTwo: " + consumptionTwo +
                "\nNow: " + consumptionNow;
    }
}
