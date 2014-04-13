package com.maxheapsize.jpm;

import javax.persistence.*;

@Entity
@Table(name = "consumption")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Long value = 0L;
    public String unit = "";


    public Consumption() {
    }

    public Consumption(Long value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}
