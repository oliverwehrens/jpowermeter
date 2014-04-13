package com.maxheapsize.jpm;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "powermeterreading")
public class PowerMeterReading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Date date = new Date();
    @OneToOne(cascade = {CascadeType.ALL})
    public Consumption consumptionTotal = new Consumption();
    @OneToOne(cascade = {CascadeType.ALL})
    public Consumption consumptionOne = new Consumption();
    @OneToOne(cascade = {CascadeType.ALL})
    public Consumption consumptionTwo = new Consumption();
    @OneToOne(cascade = {CascadeType.ALL})
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
