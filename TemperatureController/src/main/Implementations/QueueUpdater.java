package main.Implementations;

import main.Interfaces.IQueueUpdater;
import com.andy.Util.Cycle;
import com.andy.IO.IDataStore;

import java.util.Collections;
import java.util.List;

/**
 * Created by andy on 08/10/15.
 */
public class QueueUpdater extends Thread implements IQueueUpdater {

    private IDataStore dataStore;
    private Cycle nextCycle;


    public QueueUpdater(IDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void run() {
        while (!this.interrupted()) {
            List<Cycle> cycles = dataStore.getQueue();
            nextCycle = Collections.min(cycles);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Cycle getNextCycle() {
        return nextCycle;
    }
}
