package Endpoints;

import com.andy.IO.*;
import com.andy.Util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("cycles")
public class API_Cycle {

    private IDataStore dataStore;

    public API_Cycle(IDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Path("/new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newCycle(@QueryParam("date") long date,
                             @QueryParam("food") String food,
                             @QueryParam("stages") List<String> stages) {
        List<Stage> stageList = new ArrayList<>();
        for (String stage: stages) {
            stageList.add(new Gson().fromJson(stage, Stage.class));
        }
        Cycle newCycle =  new Cycle(new Date(date), food, 0, "", stageList, false);
        if (dataStore.newCycle(newCycle))
            return Response.ok().build();
        else
            return Response.serverError().build();
    }

    @Path("/queue")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQueue() {
        List<com.andy.Util.Cycle> queue = dataStore.getQueue();
        String jsonQueue = new Gson().toJson(queue);
        return Response.ok(jsonQueue, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Path("/history")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory() {
        List<com.andy.Util.Cycle> history = dataStore.getHistory();
        String jsonHistory = new Gson().toJson(history);
        return Response.ok(jsonHistory, MediaType.APPLICATION_JSON_TYPE).build();
    }
}
