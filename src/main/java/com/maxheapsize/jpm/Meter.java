package com.maxheapsize.jpm;

import java.math.BigDecimal;

public class Meter {

    public BigDecimal value = new BigDecimal(0);
    public String unit = "";

    public Meter() {
    }

    public Meter(BigDecimal value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
