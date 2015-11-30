package com.andy.IO;

import com.andy.Util.*;

import java.util.Date;
import java.util.List;

/**
 * Created by andy on 05/10/15.
 */
public interface IDataStore {

    boolean newCycle(Cycle cycle);

    List<Cycle> getQueue();

    List<Cycle> getHistory();

    Temperature getTemperature();

    List<Temperature> getTemperatures();

    boolean logTemperature(Temperature temp);

    boolean updateCycle(String id, List<Pair<String, ?>> updates);

    boolean cancelCycle(String cycleID);

    boolean moveToHistory(String cycleID);
}
