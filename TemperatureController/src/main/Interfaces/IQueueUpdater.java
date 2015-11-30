package main.Interfaces;

import com.andy.Util.Cycle;

/**
 * Created by andy on 28/10/15.
 */
public interface IQueueUpdater {
    Cycle getNextCycle();

    void start();

    void interrupt();
}
