package org.example;

import com.sybit.airtable.exception.AirtableException;
import junit.framework.TestCase;
import org.apache.http.client.HttpResponseException;

import java.lang.reflect.InvocationTargetException;

public class AirtableManagerTest extends TestCase {
    private static String apiKey = "key8khS01fFZYRQSv";
    private static String baseName = "appL7E4fvJvvYvyb3";
    private static String tableName = "Recipe";
    private AirtableManager am;
    private Rec r1;
    public void setUp() throws Exception {
        am = new AirtableManager(apiKey, baseName, tableName);
//        am = new AirtableManager(apiKey, baseName, "Practice");
        am.setupAirtable();

        r1 = new Rec();
        r1.setName("Chicken Enchilada");
        r1.setCuisine("Mexican");
        r1.setIgredients("sour cream, 0.5-cup\nchicken broth, 1.5-cup");
        r1.setInstructions("1. boil the chicken broth\n2. add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
        super.setUp();
    }

    public void testGetTable() {
    }

    public void testSetupAirtable() throws AirtableException {

    }

    public void testRetrieveAllList() throws AirtableException, HttpResponseException {
        assertEquals(0, am.getRecipeList().size());
        am.retrieveAllList();
        assertEquals(am.getRecipeList().size(), 6);
        for(int i = 0; i < am.getRecipeList().size(); i++) {
            System.out.println(am.getRecipeList().get(i).getName());
        }

        assertEquals(am.getRecipeList().get(0).getName(), "chicken ench");
//        assertEquals(am.getRecipeList().get(5).getName(), "egg tortilla");
    }


    public void testCreateRecipe() throws AirtableException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        am.createARecipe(r1);
    }
}