package org.example;

import java.util.*;
import org.example.IgdAmnt;

public class Recipe {
    private String name;
    private String cuisine;
    private Map<String, IgdAmnt> igdList;
    private Vector<String> instructList;

    Recipe() {
        this.igdList = new HashMap<>();
        this.instructList = new Vector<String>(0,1);
    }
    Recipe(String n, String c) {
        this();
        this.name = n;
        this.cuisine = c;
    }

    public void addIngd(String igd, IgdAmnt iamount) {
        igdList.put(igd, iamount);
    }

    public IgdAmnt getIgdAmount(String igd) {
        return igdList.get(igd);
    }

    /*
    print the ingredient list
     */
    public void listIgdAmount() {
        for(Map.Entry<String, IgdAmnt> it:this.igdList.entrySet()) {
            System.out.println(it.getKey() + " " + it.getValue());
        }
    }

    public void listInstructions() {
        for(int i = 0; i < this.instructList.size(); i++) {
            System.out.println(this.instructList.get(i));
        }
    }
    public void addInstruction(String step) {
        instructList.add(step);
    }

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

    public Map<String, IgdAmnt> getIgdList() {
        return igdList;
    }

    public void setIgdList(Map<String, IgdAmnt> igdList) {
        this.igdList = igdList;
    }

    public Vector<String> getInstructList() {
        return instructList;
    }

    public void setInstructList(Vector<String> instructList) {
        this.instructList = instructList;
    }
}
