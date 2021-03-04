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
    }
    public RecipeManager (AirtableManager am) {
        this();
        this.atManager = am;
    }

    public void addRecipe(Recipe r){
        this.recipeSet.add(r);
        // TODO: creat a record in airtable
        // pushToAirtable(r);
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

    private void pushToAirtable(Recipe r) throws InvocationTargetException, AirtableException, NoSuchMethodException, IllegalAccessException {
//        atManager.getTable().create(r);

        //TODO:
        //check if recipe name is duplicated
    }

    public void updateLocalRecipeList() throws AirtableException, HttpResponseException {
        // refresh the recipe list shown on end user
        this.airtableRecipe = atManager.getRecipeList();
        for(int i = 0; i < this.airtableRecipe.size(); i++) {
            this.addRecipe(new Recipe(airtableRecipe.get(i)));
        }
    }

    public void printRecipe(Recipe r) {

    }

}
