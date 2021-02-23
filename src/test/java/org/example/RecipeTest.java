package org.example;

import junit.framework.TestCase;
import java.util.*;

public class RecipeTest extends TestCase {
    protected Recipe chknEnchillada;
    protected Recipe danDanNoodle;

    public void setUp() throws Exception {
        chknEnchillada= new Recipe();
        danDanNoodle = new Recipe("dan dan noodle", "chinese");
        super.setUp();
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