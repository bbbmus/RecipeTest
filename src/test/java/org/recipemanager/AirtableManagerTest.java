package org.recipemanager;

import com.sybit.airtable.exception.AirtableException;
import junit.framework.TestCase;
import org.apache.http.client.HttpResponseException;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class AirtableManagerTest extends TestCase {
    private static String apiKey = "key8khS01fFZYRQSv";
    private static String baseName = "appL7E4fvJvvYvyb3";
    private static String tableName = "Recipe";
    private AirtableManager am;
    private RecipeManager rm;
    private Rec r1;
    private Date d;
    public void setUp() throws Exception {
        am = RecipeTest.setUpAirtable();

        d = new Date();

        rm = new RecipeManager();
        rm.addListener(am);
        rm.updateLocalRecipeList(am);

        r1 = new Rec();
        r1.setName("Chicken Enchilada" + d.getTime());
        r1.setCuisine("Mexican");
        r1.setIgredients("sour cream, 0.5-cup\nchicken broth, 1.5-cup");
        r1.setInstructions("1. boil the chicken broth\n2. add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
        super.setUp();
    }


    public void testRetrieveAllList() throws AirtableException, HttpResponseException {
        AirtableManager amLocal = new AirtableManager(apiKey, baseName, tableName);
        amLocal.setupAirtable();
        amLocal.retrieveAllList();
        assertEquals(amLocal.getRecipeList().size(), am.getRecipeList().size());
        for(int i = 0; i < amLocal.getRecipeList().size(); i++) {
            assertEquals(amLocal.getRecipeList().get(i).getName(), am.getRecipeList().get(i).getName());
        }
    }


    public void testCreateDeleteRecipe() throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, HttpResponseException {
        int preSize = am.getRecipeList().size();
        Recipe R1 = new Recipe(r1);
        rm.addRecipe(R1);
        assertEquals(am.getRecipeList().size(), preSize+1);
        rm.deleteRecipe(R1);
        assertEquals(am.getRecipeList().size(), preSize);
    }

    public void testSearchRecipe() throws AirtableException {
        assertEquals(am.searchRecipe("sweet and sour soup" + d.getTime()).size(), 0);

        Recipe R1 = new Recipe(r1);
        rm.addRecipe(R1);
        assertEquals(am.searchRecipe(R1.getName()).size(), 1);
        rm.deleteRecipe(R1);

    }
}