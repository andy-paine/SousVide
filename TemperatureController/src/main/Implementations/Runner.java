package main.Implementations;

import com.andy.Util.Pair;
import com.andy.Util.Temperature;
import main.Interfaces.IHeater;
import main.Interfaces.IQueueUpdater;
import main.Interfaces.ITemperatureProvider;
import com.andy.IO.IDataStore;
import com.andy.Util.Cycle;
import com.andy.Util.Stage;

import java.time.*;
import java.util.Date;

/**
 * Created by andy on 06/10/15.
 */
public class Runner extends Thread {

    private IHeater heater;
    private ITemperatureProvider thermometer;
    protected IDataStore dataStore;
    private IQueueUpdater queueUpdater;

    private Cycle nextCycle;

    public Runner(IHeater heater, ITemperatureProvider temperatureProvider, IDataStore dataStore, IQueueUpdater queueUpdater) {
        this.heater = heater;
        this.thermometer = temperatureProvider;
        this.dataStore = dataStore;
        this.queueUpdater = queueUpdater;
    }

    public void run() {
        queueUpdater.start();

        while (!this.isInterrupted()) {
            nextCycle = queueUpdater.getNextCycle();
            if (nextCycle != null && new Date().after(nextCycle.getDate())) {
                try {
                    executeCycle(nextCycle);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void executeCycle(Cycle cycle) throws InterruptedException {

        for (Stage stage: cycle.getStages()) {
            LocalDateTime end = LocalDateTime.now();
            end = end.plusSeconds(stage.getDuration().getSeconds());
            while (LocalDateTime.now().isBefore(end)) {
                //maintain temp
                checkTemperature(stage.getTemperature());
                sleep(5000);
                logTemperature();
            }
        }

        dataStore.moveToHistory(cycle.getId());

        heater.off();
    }

    private void checkTemperature(Double target) {
        Double temp = thermometer.getTemperature();
        if (temp < target)
            heater.on();
        else
            heater.off();
    }

    private void logTemperature() {
        Double temp = thermometer.getTemperature();
        dataStore.logTemperature(new Temperature(temp));
    }

    public void interrupt() {
        queueUpdater.interrupt();
        heater.off();
    }
}
