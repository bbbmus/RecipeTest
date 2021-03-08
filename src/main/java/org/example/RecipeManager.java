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
        recipeSet = new HashSet<>();
        airtableRecipe = new ArrayList<>();
    }
    public RecipeManager (AirtableManager am) {
        this();
        this.atManager = am;
    }

    public void addRecipe(Recipe r) throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        pushToAirtable(r);
        this.recipeSet.add(r);
        this.airtableRecipe.add(r.Recipe2Rec());
    }

    public void deleteRecipe(Recipe r) {
        this.recipeSet.remove(r);
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
        while(it.hasNext()) {
            System.out.println(it.next());
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
        String id = atManager.createARecipe(r.Recipe2Rec());
         r.setAirtableID(id);

        //TODO:
        //check if recipe name is duplicated
    }

    public void updateLocalRecipeList() throws AirtableException, HttpResponseException {
        // refresh the recipe list shown on end user
        if(airtableRecipe.isEmpty()) {
            atManager.retrieveAllList();
            this.airtableRecipe.addAll(atManager.getRecipeList());
            for(int i = 0; i < this.airtableRecipe.size(); i++) {
                this.recipeSet.add(new Recipe(airtableRecipe.get(i)));
            }
        }

    }

    public void printRecipe(Recipe r) {

    }

}
