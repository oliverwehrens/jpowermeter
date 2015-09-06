package com.maxheapsize.jpm;

import java.util.Date;

public class SmartMeterReading {

    public Date date = new Date();
    public Consumption consumptionTotal = new Consumption();
    public Consumption consumptionOne = new Consumption();
    public Consumption consumptionTwo = new Consumption();
    public Consumption consumptionNow = new Consumption();
    public boolean complete = true;

    @Override
    public String toString() {
        return complete ?
                "Reading from: " + date + " with values:" +
                        "\nTotal: " + consumptionTotal +
                        "\nOne: " + consumptionOne +
                        "\nTwo: " + consumptionTwo +
                        "\nNow: " + consumptionNow
                :
                "Not recorded.";
    }
}
