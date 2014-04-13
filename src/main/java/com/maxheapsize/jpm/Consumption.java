package com.maxheapsize.jpm;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "consumption")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public BigDecimal value = new BigDecimal(0);
    public String unit = "";


    public Consumption() {
    }

    public Consumption(BigDecimal value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
