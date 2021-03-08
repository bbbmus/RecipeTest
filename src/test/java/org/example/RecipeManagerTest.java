package org.example;

import static org.junit.Assert.*;

import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;


public class RecipeManagerTest
{
    private String apiKey;
    private String baseName;
    private String tableName;
    RecipeManager rm;
    AirtableManager am;
    private Recipe r1;
    String r1Title = "Chicken Enchilada3";
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

        rm = new RecipeManager(am);
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testUpdateLocalRecipeList() throws AirtableException, HttpResponseException {
        int beforeSize = rm.getRecipeSet().size();
        assertEquals(beforeSize, 0);
        rm.updateLocalRecipeList();
        int afterSize = rm.getRecipeSet().size();
        assertEquals(afterSize-beforeSize, afterSize);
        rm.updateLocalRecipeList();
        int afterSize2 = rm.getRecipeSet().size();
        assertEquals(afterSize2-beforeSize, afterSize);
    }

    @Test
    public void addRecipeTest() throws InvocationTargetException, AirtableException, NoSuchMethodException, IllegalAccessException, HttpResponseException {
        rm.updateLocalRecipeList();
        int ogSize = rm.getAirtableRecipe().size();
        assertEquals(rm.getRecipeSet().size(), ogSize);
        System.out.println("recipe set size: " + rm.getRecipeSet().size());
        System.out.println("recipe list size: " + rm.getAirtableRecipe().size());
        Recipe r2 = new Recipe("honey soysauce chicken", "taiwanese");
        r2.addIngd("chicken thigh", new IgdAmnt(1, "lb"));
        r2.addIngd("ginger", new IgdAmnt(4, "oz"));
        r2.addIngd("garlic", new IgdAmnt(2, "cloves"));
        r2.addInstruction("marinate the chicken");
        r2.addInstruction("cut ginger into thin slices");
        r2.addInstruction("smash the garlic");
        rm.addRecipe(r2);
        assertEquals(rm.getRecipeSet().size(), rm.getAirtableRecipe().size());

    }

    @Test
    public void testPushToAirtable() throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, HttpResponseException {
        am.retrieveAllList();
        int before = am.getRecipeList().size();
        am.createARecipe(r1.Recipe2Rec());
        am.retrieveAllList();
        int after = am.getRecipeList().size();
        assertEquals(1, after-before);

    }

    @Test
    public void testOpenRecipe() throws AirtableException, HttpResponseException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        rm.updateLocalRecipeList();
        rm.addRecipe(r1);
        rm.openRecipe(r1);

    }
}
