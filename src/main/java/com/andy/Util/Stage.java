package com.andy.Util;

import java.time.Duration;

/**
 * Created by andy on 05/10/15.
 */
public class Stage {
    private Double temperature;
    private int duration;

    public Stage(Double temperature, int minutes) {
        this.temperature = temperature;
        this.duration = minutes;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Duration getDuration() {
        return Duration.ofMinutes(this.duration);
    }

    public void setDuration(int minutes) {
        this.duration = minutes;
    }
}
