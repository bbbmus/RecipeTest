package org.recipemanager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class MealPrepManager implements PropertyChangeListener {

    private Map<String, List<IgdAmnt>> igdList;

    // small list, use vector instead of Set
    private Vector<Recipe> mealPrepLists;
    private Set<Recipe> recipeSet;

    public MealPrepManager() {
        this.igdList = new HashMap<>();
        this.mealPrepLists = new Vector<Recipe>();
        this.recipeSet = new LinkedHashSet<>();
    }

    /*
    * input: idx, the recipe idx in the recipeSet
    * */
    public void addRecipe(int idx) {

        if(idx > this.recipeSet.size()) {
            System.out.println("index out of bounds, only " + this.recipeSet.size() + " exist. this index does not exist!");
            return;
        }

        if(idx == 0) {
            System.out.println("zero is an invalid input. index starts from one.");
        }

        int cnt = 1;
        Iterator<Recipe> it = this.recipeSet.iterator();
        while(cnt < idx && it.hasNext()) {
            it.next();
            cnt++;
        }

        addRecipe((Recipe) it.next());
    }

    public void addRecipe(Recipe r) {
        this.mealPrepLists.add(r);
        return;
    }


    public void deleteRecipe(int idx) {
        try {
            this.mealPrepLists.remove(idx);
        }catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    public void deleteRecipe(Recipe r) {
        this.mealPrepLists.remove(r);
    }

    private void addIngd(Map<String, IgdAmnt> ingd) {
        for(Map.Entry<String, IgdAmnt> ingredient: ingd.entrySet()) {
            String name = ingredient.getKey();
            IgdAmnt igdamnt = ingredient.getValue();
            boolean set = false;

            if(this.igdList.containsKey(name)) {
                List<IgdAmnt> tmp = this.igdList.get(name);
                for(int i = 0; i < tmp.size(); i++) {
                    if(tmp.get(i).getUnit().equals(igdamnt.getUnit())) {
                        // case 1: ingredients and unit all match, add the amount into the same ingredient
                        double oldAmount = tmp.get(i).getAmount();
                        tmp.get(i).setAmount(oldAmount + igdamnt.getAmount());
                        set = true;
                        break;
                    }
                }

                if(!set) {
                    // case 2: this ingredient exist, but has a different unit then current unit
                    tmp.add(igdamnt);
                }

            }else if(!this.igdList.containsKey(name)) {
                // case3: this ingredient doesn't exist yet
                List<IgdAmnt> tmp = new LinkedList<>();
                tmp.add(igdamnt);
                igdList.put(name, tmp);
            }
        }
        return;
    }

    public void generateIngdList() {
        igdList.clear();
        for(int i = 0; i < this.mealPrepLists.size(); i++) {
            addIngd(mealPrepLists.get(i).getIgdList());
        }
    }

    public void showIngredientList() {
        for(Map.Entry<String, List<IgdAmnt>> igd: igdList.entrySet() ) {
            List<IgdAmnt> tmp = igd.getValue();
            String igdName = igd.getKey();
            System.out.print(igdName+ " - ");
            for(int i = 0; i < tmp.size(); i++) {
                System.out.print(tmp.get(i).getAmount() + " " + tmp.get(i).getUnit());
                if(i != tmp.size()-1) {
                    System.out.print( " + ");
                }
            }
            System.out.println("");

        }
    }


    public Map<String, List<IgdAmnt>> getIgdList() {
        return igdList;
    }

    public void setIgdList(Map<String, List<IgdAmnt>> igdList) {
        this.igdList = igdList;
    }

    public Vector<Recipe> getMealPrepLists() {
        return mealPrepLists;
    }

    public void setMealPrepLists(Vector<Recipe> mealPrepLists) {
        this.mealPrepLists = mealPrepLists;
    }

    public Set<Recipe> getRecipeSet() {
        return recipeSet;
    }

    public void setRecipeSet(Set<Recipe> recipeSet) {
        this.recipeSet = recipeSet;
    }

    public void viewAllRecipes() {
        // NOTE: common to all manager, can be extracted to be parent in the future
        Iterator it = this.recipeSet.iterator();
        int cnt = 1;
        while(it.hasNext()) {
            Recipe r = (Recipe) it.next();
            System.out.println(cnt + ". " + r.getName());
            cnt++;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Recipe changedRecipe = (Recipe) evt.getNewValue();
        if(evt.getPropertyName() == "deletedRecipe") {
            // a recipe is deleted, update local recipe set to reflect
            this.recipeSet.remove(changedRecipe);
        }else if(evt.getPropertyName() == "addedRecipe") {
            // a recipe is added
            this.recipeSet.add(changedRecipe);
        }

    }
}
