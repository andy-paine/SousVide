package com.andy.IO;

import com.andy.Util.Cycle;
import com.andy.Util.Pair;
import com.andy.Util.Temperature;
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andy on 19/11/15.
 */
public class MongoDB implements IDataStore {

    MongoCollection cycles;
    MongoCollection temperatures;
    Gson gson;

    public MongoDB() {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("sousvide");
        this.cycles = database.getCollection("cycles");
        this.temperatures = database.getCollection("temperatures");
        this.gson = new Gson();
    }

    @Override
    public boolean newCycle(Cycle cycle) {
        cycles.insertOne(Document.parse(gson.toJson(cycle)));
        return true;
    }

    @Override
    public List<Cycle> getQueue() {
        List cycleList = new ArrayList();
        Iterator it = cycles.find(new Document("completed", false)).iterator();
        while (it.hasNext())
            cycleList.add(gson.fromJson(((Document) it.next()).toJson(), Cycle.class));
        return cycleList;

    }

    @Override
    public List<Cycle> getHistory() {
        List cycleList = new ArrayList();
        Iterator it = cycles.find(new Document("completed", true)).iterator();
        while (it.hasNext()) {
            String json = ((Document)it.next()).toJson();
            cycleList.add(gson.fromJson(json, Cycle.class));
        }
        return cycleList;
    }

    @Override
    public Temperature getTemperature() {
        return null;
    }

    @Override
    public List<Temperature> getTemperatures() {
        List temps = new ArrayList<>();
        Iterator it = temperatures.find().iterator();
        while (it.hasNext()) {
            String json = ((Document) it.next()).toJson();
            temps.add(gson.fromJson(json, Temperature.class));
        }
        return temps;
    }

    @Override
    public boolean logTemperature(Temperature temp) {
        temperatures.insertOne(Document.parse(gson.toJson(temp)));
        return true;
    }

    @Override
    public boolean updateCycle(String cycleID, List<Pair<String, ?>> updates) {
        Document changes = new Document();
        for (Pair<String, ?> update: updates) {
            changes.append(update.key(), update.value());
        }
        UpdateResult result = cycles.updateOne(new Document("_id", cycleID), new Document("$set", changes));
        return result.wasAcknowledged();
    }

    @Override
    public boolean cancelCycle(String cycleID) {
        cycles.deleteOne(new Document("_id", cycleID));
        return true;
    }

    @Override
    public boolean moveToHistory(String cycleID) {
        UpdateResult result = cycles.updateOne(new Document("_id", cycleID), new Document("$set", new Document("completed", true)));
        return result.wasAcknowledged();
    }
}
