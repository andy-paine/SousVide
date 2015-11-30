package com.andy;

import com.andy.IO.MongoDB;
import com.andy.Util.Cycle;
import com.andy.Util.Pair;
import com.andy.Util.Stage;
import com.andy.Util.Temperature;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andy on 19/11/15.
 */
public class MongoDBTest {

    MongoDB db;

    @Before
    public void setup() {
        this.db = new MongoDB();
    }

    @Test
    public void testLogTemperature() {
        db.logTemperature(new Temperature(65.0));
    }

    @Test
    public void testGetTemperatures() {
        List temps = db.getTemperatures();
        System.out.print(temps);
    }

    @Test
    public void testNewCycle() {
        List stages = new ArrayList<>();
        stages.add(new Stage(55.0, 20));
        stages.add(new Stage(65.0, 10));
        Cycle cycle = new Cycle(new Date(), "Pork", 8, "", stages, false);

        db.newCycle(cycle);
    }

    @Test
    public void testCycleQueue() {
        List cycles = db.getQueue();
        System.out.print(cycles);
    }

    @Test
    public void testUpdateCycle() {
        List updates = new ArrayList<>();
        updates.add(new Pair<>("completed", true));
        db.updateCycle("76276c42-3cf1-4598-822a-e02d5b021a88", updates);
    }

   /* @Test
    public void testRemoveCycle() {
        db.cancelCycle("6a7f81bd-865a-4444-bc63-4396f5bea8cc");
    }*/

    @Test
    public void testMoveToHistory() {
        db.moveToHistory("3eb41b84-4340-4f4a-930a-75c840538839");
    }
}
