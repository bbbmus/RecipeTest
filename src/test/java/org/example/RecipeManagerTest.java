package org.example;

import static org.junit.Assert.*;

import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RecipeManagerTest
{
    private String apiKey;
    private String baseName;
    private String tableName;
    RecipeManager rm;
    AirtableManager am;
    private Recipe r1;
    String r1Title = "Chicken ench 0315";
    @Before
    public void setUp() throws AirtableException {
        apiKey = "key8khS01fFZYRQSv";
        baseName = "appL7E4fvJvvYvyb3";
        tableName = "Recipe";

        r1 = new Recipe(r1Title, "Mexican");
        r1.addIngd("sour cream", new IgdAmnt(0.5, "cup"));
        r1.addIngd("chicken broth",new IgdAmnt(1.5, "cup"));
        r1.addInstruction("boil the chicken broth");
        r1.addInstruction("add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
        r1.addInstruction("take the chicken breast out, let it cool, then shred the chicken breast");

        am = new AirtableManager(apiKey, baseName, tableName);
        am.setupAirtable();

        rm = new RecipeManager();
        rm.addListener(am);
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testUpdateLocalRecipeList() throws AirtableException, HttpResponseException {
        int beforeSize = rm.getRecipeSet().size();
        assertEquals(beforeSize, 0);
        rm.updateLocalRecipeList(am);
        int afterSize = rm.getRecipeSet().size();
        assertEquals(afterSize-beforeSize, afterSize);
        rm.updateLocalRecipeList(am);
        int afterSize2 = rm.getRecipeSet().size();
        assertEquals(afterSize2-beforeSize, afterSize);
    }

    @Test
    public void addRecipeTest() throws InvocationTargetException, AirtableException, NoSuchMethodException, IllegalAccessException, HttpResponseException {
        rm.updateLocalRecipeList(am);
        int ogSize = rm.getRecipeSet().size();

        System.out.println("recipe set size: " + rm.getRecipeSet().size());
        Recipe r2 = new Recipe("honey soysauce chicken 0315", "taiwanese");
        r2.addIngd("chicken thigh", new IgdAmnt(1, "lb"));
        r2.addIngd("ginger", new IgdAmnt(4, "oz"));
        r2.addIngd("garlic", new IgdAmnt(2, "cloves"));
        r2.addInstruction("marinate the chicken");
        r2.addInstruction("cut ginger into thin slices");
        r2.addInstruction("smash the garlic");

        assertEquals(am.getRecipeList().size(), ogSize);
        rm.addRecipe(r2);
        assertEquals(rm.getRecipeSet().size(), ogSize+1);
        assertEquals(am.getRecipeList().size(), ogSize+1);
        assertEquals(am.searchRecipe("honey soysauce chicken 0315").size(), 1);

        rm.addRecipe(new Recipe("chicken jerky", "dog treats"));
        rm.addRecipe(new Recipe("cheese cake", "american desert"));



    }

    @Test
    public void testPushToAirtable() throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, HttpResponseException {
        am.retrieveAllList();
        int before = am.getRecipeList().size();
        am.createARecipe(r1.getRec());
        am.retrieveAllList();
        int after = am.getRecipeList().size();
        assertEquals(1, after-before);

    }

    @Test
    public void testOpenRecipe() throws AirtableException, HttpResponseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        rm.updateLocalRecipeList(am);
        rm.addRecipe(r1);
        rm.openRecipe(r1);

    }

    @Test
    public void testDeleteRecipe() throws InvocationTargetException, AirtableException, NoSuchMethodException, IllegalAccessException, HttpResponseException {
        rm.updateLocalRecipeList(am);
        int beforenofRecipes = rm.getRecipeSet().size();
        rm.addRecipe(r1);
        List<Rec> tmp = am.searchRecipe(r1Title);
        assertEquals(tmp.size(), 1);
        assertEquals(rm.getRecipeSet().size()-beforenofRecipes, 1);

        rm.deleteRecipe(r1);
        assertEquals(rm.getRecipeSet().size(), beforenofRecipes+1);
        tmp = am.searchRecipe(r1Title);
        assertEquals(tmp.size(), 0);

//        rm.deleteRecipe();
//        tmp = am.searchRecipe("honey soysauce chicken 0315");
//        assertEquals(tmp.size(), 1);
//        rm.deleteRecipe(14);
//        assertEquals(rm.getRecipeSet().size(), beforenofRecipes-1);
//        assertEquals(am.getRecipeList().size(), rm.getRecipeSet().size());
//        tmp = am.searchRecipe("honey soysauce chicken");
//        assertEquals(tmp.size(), 0);
    }

    @Test
    public void testViewAllRecipes() throws AirtableException, HttpResponseException {
        rm.updateLocalRecipeList(am);
        System.out.println("numbers of recipes: " + rm.getRecipeSet().size());
        rm.viewAllRecipes();
    }
}
