package org.example;

/**
 * Hello world!
 *
 */

import com.mashape.unirest.request.GetRequest;
import com.sybit.airtable.*;
import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RecipeManager
{
    private List<Rec> airtableRecipe;
    private Set<Recipe> recipeSet;
    private AirtableManager atManager;

    public RecipeManager() {
        recipeSet = new LinkedHashSet<>();
        airtableRecipe = new ArrayList<>();
    }
    public RecipeManager (AirtableManager am) {
        this();
        this.atManager = am;
    }

    public void addRecipe(Recipe r) throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        pushToAirtable(r);
        this.recipeSet.add(r);
    }

    public void deleteRecipe(Recipe r) throws AirtableException {
        this.recipeSet.remove(r);
        atManager.deleteARecipe(r.getRec());
    }

    public void deleteRecipe(int idx) throws AirtableException {
        if(idx >= recipeSet.size()) {
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

    public List<Rec> getAirtableRecipe() {
        return airtableRecipe;
    }

    public void setAirtableRecipe(List<Rec> airtableRecipe) {
        this.airtableRecipe = airtableRecipe;
    }

    public void viewAllRecipes() {
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

    public void updateLocalRecipeList() throws AirtableException, HttpResponseException {
        // refresh the recipe list shown on end user
        if(recipeSet.isEmpty()) {
            atManager.retrieveAllList();
            List<Rec> tmp = atManager.getRecipeList();
            for(int i = 0; i < tmp.size(); i++) {
                this.recipeSet.add(new Recipe(tmp.get(i)));
            }
        }

    }

    public void printRecipe(Recipe r) {

    }

}
