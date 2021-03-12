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
    private Rec danDanNoodleRec;

    public void setUp() throws Exception {
        chknEnchillada= new Recipe();
        danDanNoodle = new Recipe("dan dan noodle", "chinese");
        danDanNoodle.addIngd("noodle", new IgdAmnt(8, "oz"));
        danDanNoodle.addInstruction("boil water");
        danDanNoodle.addInstruction("cook minced pork");

        danDanNoodleRec = new Rec();
        danDanNoodleRec.setName(danDanNoodle.getName());
        danDanNoodleRec.setCuisine(danDanNoodle.getCuisine());
        danDanNoodleRec.setIgredients(danDanNoodle.getIngredients());
        danDanNoodleRec.setInstructions(danDanNoodle.getInstructions());

        apiKey = "key8khS01fFZYRQSv";
        baseName = "appL7E4fvJvvYvyb3";
        tableName = "Recipe";
        am = new AirtableManager(apiKey, baseName, tableName);
        this.am.setupAirtable();
        super.setUp();
    }

    public void testConstructorRec() throws AirtableException, HttpResponseException {
        am.retrieveAllList();
        steak = new Recipe(am.getRecipeList().get(1));
        assertEquals("steak", steak.getName());
        assertEquals("american", steak.getCuisine());
        assertEquals(3, steak.getIgdList().size());
        assertEquals(4, steak.getInstructList().size());
        steak.listIgdAmount();
        steak.listInstructions();

        Recipe eggtortilla = new Recipe(am.getRecipeList().get(2));
        assertEquals("Chicken Enchilada2", eggtortilla.getName());
        assertEquals("Mexican", eggtortilla.getCuisine());
        eggtortilla.listIgdAmount();
        eggtortilla.listInstructions();
    }

    public void testAddIngd() {
        String sbefore = chknEnchillada.getIngredients();
        assertEquals(sbefore.length(), 0);
        chknEnchillada.addIngd("chicken breast", new IgdAmnt(1.5, "lb"));
        IgdAmnt ret = chknEnchillada.getIgdAmount("chicken breast");
        String safter = chknEnchillada.getIngredients();
        assertEquals(safter.length(), "chicken breast,1.5-lb\n".length());
        assertEquals(ret.getAmount(), 1.5);
        assertEquals(ret.getUnit(), "lb");

    }

    public void testAddInstruction() {
        Vector<String> ret = chknEnchillada.getInstructList();

        String sbef = chknEnchillada.getInstructions();
        assertEquals(sbef.length(), 0);
        chknEnchillada.addInstruction("boil the chicken broth");
        String saft = chknEnchillada.getInstructions();
        assertEquals(saft.length(), "1. boil the chicken broth\n".length());
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

    public void testRecipe2Rec() {
        Rec dandan = danDanNoodle.getRec();
        assertEquals(dandan.getName(), danDanNoodleRec.getName());
        assertEquals(dandan.getInstructions(), danDanNoodleRec.getInstructions());
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