package org.example;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Rec {

    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("cuisine")
    private String cuisine;

    @SerializedName("ingredients")
    private String igredients;

    @SerializedName("instructions")
    private String instructions;
    private Date createdTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getIgredients() {
        return igredients;
    }

    public void setIgredients(String igredients) {
        this.igredients = igredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
