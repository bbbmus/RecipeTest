package org.example;

import static org.junit.Assert.*;

import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;
import org.junit.Before;
import org.junit.Test;



public class RecipeManagerTest
{
    private String apiKey;
    private String baseName;
    private String tableName;
    RecipeManager rm;

    @Before
    public void setUp() throws AirtableException {
        apiKey = "key8khS01fFZYRQSv";
        baseName = "appL7E4fvJvvYvyb3";
        tableName = "Recipe";
        Recipe r1 = new Recipe("Chicken Enchilada", "Mexican");
        r1.addIngd("sour cream", new IgdAmnt(0.5, "cup"));
        r1.addIngd("chicken broth",new IgdAmnt(1.5, "cup"));
        r1.addInstruction("boil the chicken broth");
        r1.addInstruction("add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
        r1.addInstruction("take the chicken breast out, let it cool, then shred the chicken breast");

        AirtableManager am = new AirtableManager(apiKey, baseName, tableName);
        am.setupAirtable();
        rm = new RecipeManager(am);
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void addRecipeTest() {
        assertTrue(rm.getRecipeSet() == null);

        Recipe r2 = new Recipe("egg tortilla", "taiwanese");
        r2.addIngd("egg", new IgdAmnt(1, ""));
        r2.addIngd("tortilla wrap", new IgdAmnt(1, ""));
        r2.addInstruction("heat up oil under medium heat");
        r2.addInstruction("beaten egg");
        r2.addInstruction("pour egg into pan and immediately cover the egg with tortilla wrap");

        rm.addRecipe(r2);
        assertEquals(rm.getRecipeSet().size(), 1);
    }

    @Test
    public void testUpdateLocalRecipeList() throws AirtableException, HttpResponseException {
        int beforeSize = rm.getRecipeSet().size();
        assertEquals(beforeSize, 0);
        rm.updateLocalRecipeList();
        int afterSize = rm.getRecipeSet().size();
        assertEquals(afterSize-beforeSize, 2);

    }
}
