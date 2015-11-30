package com.andy.Util;

import java.util.Date;

/**
 * Created by andy on 19/11/15.
 */
public class Temperature {

    private Double temperature;
    private Date date;

    public Temperature(Double temperature) {
        this.temperature = temperature;
        this.date = new Date();
    }

    public Temperature(Double temperature, Date date) {
        this.temperature = temperature;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "temperature=" + temperature +
                ", date=" + date +
                '}';
    }

    public Date date() {
        return this.date;
    }

    public Double temperature() {
        return this.temperature;
    }
}
