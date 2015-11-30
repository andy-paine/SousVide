package test;

import com.andy.IO.IDataStore;
import com.andy.Util.*;
import main.Implementations.*;
import main.Interfaces.*;
import org.junit.*;
import test.mocks.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 29/10/15.
 */
public class RunnerTest {

    Runner runner;

    @Before
    public void setUp() {
        IHeater heater = new MockHeater();
        ITemperatureProvider temperatureProvider = new USBThermometer();
        IDataStore dataStore = new MockDB();

        List<Stage> stages = new ArrayList<>();
        stages.add(new Stage(25.0, 1));
        stages.add(new Stage(28.0, 1));
        Cycle cycle = new Cycle(new Date(new java.util.Date().getTime()+5000), "Test", 0, "", stages, false);
        dataStore.newCycle(cycle);

        IQueueUpdater queueUpdater = new QueueUpdater(dataStore);

        this.runner = new Runner(heater, temperatureProvider, dataStore, queueUpdater);

    }

    @Test
    public void testFullRunning() throws IOException{
        this.runner.start();
        System.in.read();
        this.runner.interrupt();
    }

}
