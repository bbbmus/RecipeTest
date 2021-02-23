package org.example;

/**
 * Hello world!
 *
 */

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.sybit.airtable.*;
import com.sybit.airtable.exception.AirtableException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.mashape.unirest.http.Unirest;
import com.sybit.airtable.vo.RecordItem;

public class RecipeManager
{

    private Set<Recipe> recipeList;
    private AirtableManager atManager;

    public RecipeManager (AirtableManager am) {
        this.atManager = am;
//        this.recipeList = am.retrieveFromAirT();
        // pull from airtable
    }

    public void addRecipe(Recipe r) {
        this.recipeList.add(r);
    }

    public void deleteRecipe(Recipe r) {
        this.recipeList.remove(r);
    }

    public Set<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(Set<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void viewAllRecipes() {
        Iterator it = recipeList.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void openRecipe(Recipe r) {
        System.out.println("Recipe Name: " + r.getName());
        System.out.println("Ingredients: \n");
        r.listIgdAmount();
        System.out.println("Cooking Instructions: \n");
        r.listInstructions();
    }

    public void printRecipe(Recipe r) {

    }

    private void updateDB() {
        // refresh the recipe list shown on GUI
    }

    private static String apiKey = "key8khS01fFZYRQSv";
    private static String baseName = "appL7E4fvJvvYvyb3";
    private static String tableName = "Practice";
    private static String url = "https://api.airtable.com/v0/appL7E4fvJvvYvyb3/Practice/recSpvptEMB7DV6QK";
    public static void main( String[] args ) throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println( "Hello World!" );
        Airtable at = new Airtable();
        at.configure(apiKey);
        Base base = at.base(baseName);
        Table<Practice> practiceTable = base.table(tableName, Practice.class);
        GetRequest response;
        Practice pt = new Practice();
        pt.setBday(1208);
        pt.setName("Uni");
        practiceTable.create(pt);

    }
}
