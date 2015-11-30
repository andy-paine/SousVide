package com.andy.Util;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by andy on 05/10/15.
 */
public class Cycle implements Comparable<Cycle>{
    private String _id;
    private Date date;
    private String food;
    private int rating;
    private String comments;
    private List<Stage> stages;
    private boolean completed;

    public Cycle() {

    }

    public Cycle(Date date, String food, int rating, String comments, List<Stage> stages, boolean completed) {
        this._id = UUID.randomUUID().toString();
        this.date = date;
        this.food = food;
        this.rating = rating;
        this.comments = comments;
        this.stages = stages;
        this.completed = completed;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void addStage(Stage stage) {
        this.stages.add(stage);
    }

    @Override
    public int compareTo(Cycle cycle) {
        return this.date.compareTo(cycle.date);
    }
}
