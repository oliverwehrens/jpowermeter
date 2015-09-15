package com.maxheapsize.jpm;

import java.util.Date;

public class SmartMeterReading {

    public Date date = new Date();
    public Meter meterTotal = new Meter();
    public Meter meterOne = new Meter();
    public Meter meterTwo = new Meter();
    public Meter power = new Meter();
    public boolean complete = true;

    @Override
    public String toString() {
        return complete ?
                "Reading from: " + date + " with values:" +
                        "\nTotal: " + meterTotal +
                        "\nOne: " + meterOne +
                        "\nTwo: " + meterTwo +
                        "\nNow: " + power
                :
                "Not recorded.";
    }
}
