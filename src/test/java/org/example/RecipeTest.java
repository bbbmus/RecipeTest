package org.example;

import com.sybit.airtable.exception.AirtableException;
import junit.framework.TestCase;
import org.apache.http.client.HttpResponseException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class RecipeTest extends TestCase {
    private Recipe chknEnchillada;
    private Recipe steak;
    private Recipe danDanNoodle;
    static private String apiKey;
    static private String baseName;
    static private String tableName;
    static private AirtableManager am;
    private Rec danDanNoodleRec;
    private RecipeManager rm;

    public void setUpDanDanNoodle() {
        // set up danDanNoodle
        danDanNoodle = new Recipe("dan dan noodle", "chinese");
        danDanNoodle.addIngd("noodle", new IgdAmnt(8, "oz"));
        danDanNoodle.addInstruction("boil water");
        danDanNoodle.addInstruction("cook minced pork");
    }

    public void setUpDanDanNoodelRec() {
        // set up danDanNoodleRec
        danDanNoodleRec = new Rec();
        danDanNoodleRec.setName(danDanNoodle.getName());
        danDanNoodleRec.setCuisine(danDanNoodle.getCuisine());
        danDanNoodleRec.setIgredients(danDanNoodle.getIngredients());
        danDanNoodleRec.setInstructions(danDanNoodle.getInstructions());
    }

    public void setUpSteak() {
        // set up steak
        steak = new Recipe("abc", "american");
        steak.addIngd("garlic", new IgdAmnt(1, "clove"));
        steak.addIngd("salt", new IgdAmnt(0.5, "tsp"));
        steak.addInstruction("pat dry the steak and sprinkle salt");
        steak.addInstruction("heat up oil, medium heat");
    }

    public static AirtableManager setUpAirtable() throws AirtableException {
        apiKey = "key8khS01fFZYRQSv";
        baseName = "appL7E4fvJvvYvyb3";
        tableName = "Recipe";
        am = new AirtableManager(apiKey, baseName, tableName);
        am.setupAirtable();

        return am;
    }

    public void setUpRecipeManager() {
        rm = new RecipeManager();
        rm.addListener(am);
    }

    public void setUp() throws Exception {
        chknEnchillada= new Recipe();

        setUpDanDanNoodle();
        setUpDanDanNoodelRec();
        setUpSteak();
        setUpAirtable();
        setUpRecipeManager();

        super.setUp();
    }

    public void testConstructorRec() throws AirtableException, HttpResponseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        rm.updateLocalRecipeList(am);
        rm.addRecipe(steak);
        Rec steakRecTest = am.getRecipeList().get(am.getRecipeList().size()-1);

        Recipe steakTest = new Recipe(steakRecTest);
        assertEquals(steak.getName(), steakTest.getName());
        assertEquals(steak.getCuisine(), steakTest.getCuisine());
        assertEquals(steak.getIgdList().size(), steakTest.getIgdList().size());
        for(Map.Entry<String, IgdAmnt> it:steak.getIgdList().entrySet()) {
            assertEquals(steakTest.getIgdList().get(it.getKey()).getUnit(), it.getValue().getUnit());
            assertEquals(steakTest.getIgdList().get(it.getKey()).getAmount(), it.getValue().getAmount());
        }

        assertEquals(steak.getInstructList().size(), steakTest.getInstructList().size());
        assertTrue(steak.getInstructList().equals(steakTest.getInstructList()));

        rm.deleteRecipe(steak);
    }

    public void testAddIngd() {
        String sbefore = chknEnchillada.getIngredients();
        assertEquals(sbefore.length(), 0);

        String ingredient = "chicken breast";
        double amount = 1.5;
        String unit = "lb";

        chknEnchillada.addIngd(ingredient, new IgdAmnt(amount, unit));
        IgdAmnt ret = chknEnchillada.getIgdAmount(ingredient);
        String safter = chknEnchillada.getIngredients();

        String tmp = ingredient + "," + amount + "-" + unit + "\n";
        assertEquals(safter.length(), tmp.length());
        assertEquals(ret.getAmount(), amount);
        assertEquals(ret.getUnit(), unit);

        String ingredient2 = "abc";
        double amount2 = 20;
        String unit2 = "";
        chknEnchillada.addIngd(ingredient2, new IgdAmnt(amount2, unit2));
        ret = chknEnchillada.getIgdAmount(ingredient2);
        String safter2 = chknEnchillada.getIngredients();

        String tmp2 = ingredient2 + "," + amount2 + "-" + unit2 + "\n";
        assertEquals(safter2.length(), tmp2.length()+tmp.length());
        assertEquals(ret.getAmount(), amount2);
        assertEquals(ret.getUnit(), unit2);
    }

    public void testAddInstruction() {
        Vector<String> ret = chknEnchillada.getInstructList();

        String sbef = chknEnchillada.getInstructions();
        assertEquals(sbef.length(), 0);
        chknEnchillada.addInstruction("boil the chicken broth");
        String saft = chknEnchillada.getInstructions();
        assertEquals(saft.length(), "1. boil the chicken broth\n".length());
        assertEquals(ret.get(0), "1. boil the chicken broth");

        chknEnchillada.addInstruction("add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
        assertEquals(ret.get(1), "2. add chicken breast into the boiled chicken broth and let it simmer until fullly cooked");
    }


    public void testRecipe2Rec() {
        Rec dandan = danDanNoodle.getRec();
        assertEquals(dandan.getName(), danDanNoodleRec.getName());
        assertEquals(dandan.getInstructions(), danDanNoodleRec.getInstructions());
    }

}