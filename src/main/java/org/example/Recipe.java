package org.example;

import java.util.*;

public class Recipe {

    private String name;
    private String cuisine;

    private String airtableID;
    private String ingredients;
    private String instructions;
    private Date createdTime;

    private Map<String, IgdAmnt> igdList;
    private Vector<String> instructList;


    Recipe() {
        this.igdList = new HashMap<>();
        this.instructList = new Vector<String>(0,1);
        this.ingredients = "";
        this.instructions = "";
    }

    Recipe(String n, String c) {
        this();
        this.name = n;
        this.cuisine = c;
    }

    Recipe(Rec r) {
        this();
        airtableRec2Recipe(r);
    }

    public void addIngd(String igd, IgdAmnt iamount) {

        igdList.put(igd, iamount);
        ingredients = ingredients + igd + "," + iamount.getAmount() + "-" + iamount.getUnit() + "\n";
    }

    public IgdAmnt getIgdAmount(String igd) {
        return igdList.get(igd);
    }

    /*
    print the ingredient list
     */
    public void listIgdAmount() {
        for(Map.Entry<String, IgdAmnt> it:this.igdList.entrySet()) {
            System.out.println(it.getKey() + " " + it.getValue().getAmount() + " " + it.getValue().getUnit());
        }
    }

    public void listInstructions() {
        for(int i = 0; i < this.instructList.size(); i++) {
            System.out.println(this.instructList.get(i));
        }
    }
    public void addInstruction(String step) {
        instructList.add(step);
        instructions = instructions + instructList.size() + ". " + step + "\n";
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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

    public String getAirtableID() {
        return airtableID;
    }

    public void setAirtableID(String airtableID) {
        this.airtableID = airtableID;
    }

    public Rec Recipe2Rec() {
        Rec rec = new Rec();
        rec.setName(this.getName());
        rec.setCuisine(this.getCuisine());
        rec.setIgredients(ingredients);
        rec.setInstructions(instructions);
        return rec;
    }
    private void airtableRec2Recipe(Rec rec) {
        this.setAirtableID(rec.getId());
        this.setName(rec.getName());
        this.setCuisine(rec.getCuisine());
        this.airtableIngd2Ingd(rec.getIgredients());
        this.airtableInst2Instruction(rec.getInstructions());
    }

    private void airtableIngd2Ingd(String igds) {
        String[] igdsArray = igds.split("\n");
        for(int i = 0; i < igdsArray.length; i++) {
            String[] igd = igdsArray[i].split(",");
            String[] amtUnit = igd[1].split("-");
            IgdAmnt igdmnt = new IgdAmnt(Double.parseDouble(amtUnit[0]));
            if(amtUnit.length > 1) {
                igdmnt.setUnit(amtUnit[1]);
            }
            this.addIngd(igd[0], igdmnt);
        }

        return ;
    }

    private void airtableInst2Instruction(String s) {
        String[] steps = s.split("\n");
        Vector<String> ret = new Vector<String>();
        for(int i = 0; i < steps.length; i++) {
            this.addInstruction(steps[i]);
        }

        return;
    }
}
