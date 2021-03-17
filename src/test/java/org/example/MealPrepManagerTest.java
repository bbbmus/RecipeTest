package org.example;

import com.sybit.airtable.exception.AirtableException;
import junit.framework.TestCase;

public class MealPrepManagerTest extends TestCase {

    private RecipeManager rm;
    private MealPrepManager mm;
    private Recipe r4;
    private Recipe r5;
    public void setUp() {
        rm = new RecipeManager();
        mm = new MealPrepManager();
        rm.addListener(mm);

        rm.addRecipe(new Recipe("recipe1", "test"));
        rm.addRecipe(new Recipe("recipe2", "test"));
        rm.addRecipe(new Recipe("recipe3", "test"));

        r4 = new Recipe("recipe4", "test");
        r4.addIngd("cumin", new IgdAmnt(1, "tsp"));
        r4.addIngd("pepper", new IgdAmnt(1, "tbsp"));
        r4.addIngd("chicken", new IgdAmnt(6, "pieces"));

        r5 = new Recipe("recipe5", "test");
        r5.addIngd("pepper", new IgdAmnt(1, "tbsp"));
        r5.addIngd("chicken", new IgdAmnt(1, "lb"));

    }
    public void testAddRecipe() {
        mm.addRecipe(2);
        mm.addRecipe(3);

        assertEquals(mm.getMealPrepLists().size(), 2);
        return;
    }

    public void testdeleteRecipeindex() {
        mm.addRecipe(2);
        mm.addRecipe(3);

        mm.deleteRecipe(1);
        assertEquals(mm.getMealPrepLists().size(), 1);
    }

    public void testdeleteReciperecipe() {
        mm.addRecipe(2);
        mm.addRecipe(3);
        mm.addRecipe(r4);

        mm.deleteRecipe(r4);
        assertEquals(mm.getMealPrepLists().size(), 2);
    }

    public void testRecipesetManagement() throws AirtableException {
        assertEquals(mm.getRecipeSet().size(), 3);
        rm.deleteRecipe(1);
        assertEquals(mm.getRecipeSet().size(), 2);

        rm.addRecipe(new Recipe("test 5", "test"));
        assertEquals(mm.getRecipeSet().size(), 3);
    }


    public void testGenerateIngdList() {
        mm.addRecipe(1);
        mm.addRecipe(3);

        mm.generateIngdList();
        assertEquals(mm.getIgdList().size(), 0);
    }

    public void testShowIngredientList() {
        mm.addRecipe(r4);
        mm.generateIngdList();
        mm.showIngredientList();

        mm.addRecipe(r5);

        mm.generateIngdList();
        mm.showIngredientList();

        assertEquals(mm.getIgdList().size(), 3);

    }
}