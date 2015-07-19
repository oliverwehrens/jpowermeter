package com.maxheapsize.jpm;

import java.math.BigDecimal;
import java.util.Date;

public class SmartMeterReadout {

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
