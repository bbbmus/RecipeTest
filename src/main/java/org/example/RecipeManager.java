package org.example;

/**
 * Hello world!
 *
 */

import com.mashape.unirest.request.GetRequest;
import com.sybit.airtable.*;
import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;

import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.beans.PropertyChangeSupport;

public class RecipeManager
{
    // observable of the observer pattern
    private PropertyChangeSupport pcs;

    private Set<Recipe> recipeSet;
    private AirtableManager atManager;
    private Recipe deletedRecipe;
    private Recipe addedRecipe;


    public RecipeManager() {
        pcs = new PropertyChangeSupport(this);
        recipeSet = new LinkedHashSet<>();
    }

    public void addListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public RecipeManager (AirtableManager am) {
        this();
        this.atManager = am;
    }

    public void addRecipe(Recipe r){
        // NOTE: common to all manager, can be extracted to be parent in the future
        this.recipeSet.add(r);
        this.addedRecipe = r;
        pcs.firePropertyChange("addedRecipe", null, addedRecipe);
        this.addedRecipe = null;
    }

    public void deleteRecipe(Recipe r) throws AirtableException {
        // NOTE: common to all manager, can be extracted to be parent in the future
        deletedRecipe = r;
        this.recipeSet.remove(r);
        pcs.firePropertyChange("deletedRecipe", null, deletedRecipe);
        deletedRecipe = null;
    }

    public void deleteRecipe(int idx) throws AirtableException {
        if(idx > recipeSet.size()) {
            System.out.println("index out of bounds, only " + recipeSet.size() + " exist. this index does not exist!");
            return;
        }

        if(idx == 0) {
            System.out.println("zero is an invalid input. index starts from one.");
        }

        int cnt = 1;
        Iterator<Recipe> it = recipeSet.iterator();
        while(cnt < idx && it.hasNext()) {
            it.next();
            cnt++;
        }

        deleteRecipe((Recipe)it.next());
    }

    public Set<Recipe> getRecipeSet() {
        return recipeSet;
    }

    public void setRecipeSet(Set<Recipe> recipeSet) {
        this.recipeSet = recipeSet;
    }

    public void viewAllRecipes() {
        // NOTE: common to all manager, can be extracted to be parent in the future
        Iterator it = recipeSet.iterator();
        int cnt = 1;
        while(it.hasNext()) {
            Recipe r = (Recipe) it.next();
            System.out.println(cnt + ". " + r.getName());
            cnt++;
        }
    }

    public void openRecipe(Recipe r) {
        // TODO: move to recipe
        System.out.println("Recipe Name: " + r.getName());
        System.out.println("Ingredients: \n");
        r.listIgdAmount();
        System.out.println("Cooking Instructions: \n");
        r.listInstructions();
    }

    public void pushToAirtable(Recipe r) throws InvocationTargetException, AirtableException, NoSuchMethodException, IllegalAccessException {

        // TODO: creat a record in airtable
        Rec ret = atManager.createARecipe(r.getRec());
        r.setAirtableID(ret.getId());
        r.setRec(ret);


        //TODO:
        //check if recipe name is duplicated
    }

    public void updateLocalRecipeList(AirtableManager am) throws AirtableException, HttpResponseException {
        // refresh the recipe list shown on end user
        // NOTE: can be extracted to the parent class
        if(recipeSet.isEmpty()) {
            am.retrieveAllList();
            List<Rec> tmp = am.getRecipeList();
            for(int i = 0; i < tmp.size(); i++) {
                addRecipe(new Recipe(tmp.get(i)));
            }
        }

    }

    public void printRecipe(Recipe r) {

    }

}
