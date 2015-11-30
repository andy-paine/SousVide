package test;

import com.andy.Util.Cycle;
import com.andy.Util.Stage;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andy on 19/11/15.
 */
public class SerializationTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testSerialization() {
        List stages = new ArrayList<>();
        stages.add(new Stage(55.0, 20));
        stages.add(new Stage(65.0, 10));
        Cycle cycle = new Cycle(new Date(), "Pork", 8, "", stages, false);

        Gson gson = new Gson();
        System.out.println(gson.toJson(cycle));

    }

    @Test
    public void testStageSerialization() {
        System.out.println(new Gson().toJson(new Stage(55.0, 20)));
    }
}
