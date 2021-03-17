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



    private Rec rec;


    Recipe() {
        this.igdList = new HashMap<>();
        this.instructList = new Vector<String>(0,1);
        this.rec = new Rec();
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
        this.rec = r;
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
        this.rec.setName(name);
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

    public Recipe clone() {
        Recipe clone = new Recipe();
        clone.setRec(this.getRec());
        clone.setAirtableID(this.getAirtableID());
        clone.setCuisine(this.getCuisine());
        clone.setName(this.getName());
        clone.setInstructions(this.getInstructions());
        clone.setInstructList(this.getInstructList());
        clone.setIgdList(this.getIgdList());
        clone.setIngredients(this.getIngredients());
        clone.setCreatedTime(this.getCreatedTime());

        return clone;
    }
    public Rec getRec() {
        if(this.rec.getName() == null) {
            Recipe2Rec();
        }
        return rec;
    }

    public void setRec(Rec r) {
        this.rec = r;
    }
    private void Recipe2Rec() {
        this.rec.setName(this.getName());
        this.rec.setCuisine(this.getCuisine());
        this.rec.setIgredients(ingredients);
        this.rec.setInstructions(instructions);
        return;
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
