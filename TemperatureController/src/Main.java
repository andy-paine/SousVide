import com.andy.IO.MongoDB;
import main.Implementations.*;
import main.Interfaces.IHeater;
import main.Interfaces.IQueueUpdater;
import main.Interfaces.ITemperatureProvider;
import com.andy.IO.IDataStore;
import com.andy.IO.PostgresDB;

import java.io.IOException;

/**
 * Created by andy on 28/10/15.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        IHeater heater = new Heater();
        ITemperatureProvider temperatureProvider = new USBThermometer();
        IDataStore dataStore = new MongoDB();
        IQueueUpdater queueUpdater = new QueueUpdater(dataStore);

        Runner runner = new Runner(heater, temperatureProvider, dataStore, queueUpdater);

        runner.start();
        System.in.read();
        runner.interrupt();
    }
}
