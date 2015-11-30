package test.mocks;

import com.andy.IO.IDataStore;
import com.andy.Util.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by andy on 29/10/15.
 */
public class MockDB implements IDataStore {

    List<Cycle> cycleQueue = new ArrayList<>();
    List<Cycle> cycleHistory = new ArrayList<>();
    List<Temperature> temperatures = new ArrayList<>();

    @Override
    public boolean newCycle(Cycle cycle) {
        cycleQueue.add(cycle);
        return true;
    }

    @Override
    public List<Cycle> getQueue() {
        return cycleQueue;
    }

    @Override
    public List<Cycle> getHistory() {
        return cycleHistory;
    }

    @Override
    public Temperature getTemperature() {
        return temperatures.get(temperatures.size()-1);
    }

    @Override
    public List<Temperature> getTemperatures() {
        return temperatures;
    }

    @Override
    public boolean logTemperature(Temperature temp) {
        Double temperature = temp.temperature();
        Date date = temp.date();
        System.out.println("Temp: " + temperature);
        temperatures.add(new Temperature(temperature, date));
        return true;
    }

    @Override
    public boolean updateCycle(String id, List<Pair<String, ?>> updates) {
        return false;
    }

    @Override
    public boolean cancelCycle(String cycleID) {
        return false;
    }

    @Override
    public boolean moveToHistory(String cycleID) {
        Cycle remove = null;
        for (Cycle cycle: cycleQueue)
            if (cycle.getId().matches(cycleID))
                remove = cycle;
        cycleQueue.remove(remove);
        cycleHistory.add(remove);
        return true;
    }
}
