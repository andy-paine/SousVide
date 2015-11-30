package test.mocks;

import main.Interfaces.IHeater;

/**
 * Created by andy on 29/10/15.
 */
public class MockHeater implements IHeater {
    @Override
    public void on() {
        System.out.println("Heater ON");
    }

    @Override
    public void off() {
        System.out.println("Heater OFF");
    }
}
