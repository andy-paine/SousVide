package main.Implementations;

import main.Interfaces.IHeater;
import com.pi4j.io.gpio.*;

/**
 * Created by andy on 07/10/15.
 */
public class Heater implements IHeater {

    GpioPinDigitalOutput heater;
    GpioController controller;

    public Heater() {
        this.controller = GpioFactory.getInstance();
        this.heater = controller.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Heater", PinState.LOW);
        controller.setShutdownOptions(true, PinState.LOW);
    }

    @Override
    public void on() {
        heater.high();
    }

    @Override
    public void off() {
        heater.low();
    }
}
