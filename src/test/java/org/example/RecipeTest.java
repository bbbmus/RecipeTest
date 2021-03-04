package org.example;

import com.sybit.airtable.exception.AirtableException;
import junit.framework.TestCase;
import org.apache.http.client.HttpResponseException;

import java.util.*;

public class RecipeTest extends TestCase {
    protected Recipe chknEnchillada;
    protected Recipe danDanNoodle;
    protected Recipe steak;
    private String apiKey;
    private String baseName;
    private String tableName;
    private AirtableManager am;
    public void setUp() throws Exception {
        chknEnchillada= new Recipe();
        danDanNoodle = new Recipe("dan dan noodle", "chinese");

        apiKey = "key8khS01fFZYRQSv";
        baseName = "appL7E4fvJvvYvyb3";
        tableName = "Recipe";
        am = new AirtableManager(apiKey, baseName, tableName);
        this.am.setupAirtable();
        super.setUp();
    }

    public void testConstructorRec() throws AirtableException, HttpResponseException {
        steak = new Recipe(am.getRecipeList().get(1));
        assertEquals("steak", steak.getName());
        assertEquals("american", steak.getCuisine());
        assertEquals(3, steak.getIgdList().size());
        assertEquals(4, steak.getInstructList().size());
        steak.listIgdAmount();
        steak.listInstructions();

    }

    public void testAddIngd() {
        chknEnchillada.addIngd("chicken breast", new IgdAmnt(1.5, "lb"));
        IgdAmnt ret = chknEnchillada.getIgdAmount("chicken breast");
        assertEquals(ret.getAmount(), 1.5);
        assertEquals(ret.getUnit(), "lb");
    }

    public void testAddInstruction() {
        Vector<String> ret = chknEnchillada.getInstructList();

        chknEnchillada.addInstruction("boil the chicken broth");

        assertEquals(ret.get(0), "boil the chicken broth");

        chknEnchillada.addInstruction("add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
        assertEquals(ret.get(1), "add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
    }

    public void testGetName() {
        assertEquals(danDanNoodle.getName(), "dan dan noodle");
    }
    public void testTestSetName() {
        chknEnchillada.setName("Chicken Enchillada");
        assertEquals("Chicken Enchillada", chknEnchillada.getName());
    }

    public void testGetCuisine() {
        assertEquals(danDanNoodle.getCuisine(), "chinese");
    }

    public void testSetCuisine() {
        chknEnchillada.setCuisine("mexican");
        assertEquals(chknEnchillada.getCuisine(), "mexican");
    }

//    public void testGetIgdList() {
//    }
//
//    public void testSetIgdList() {
//    }
//
//    public void testGetInstructList() {
//    }
//
//    public void testSetInstructList() {
//    }
}