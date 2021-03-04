package org.example;

import com.sybit.airtable.exception.AirtableException;
import junit.framework.TestCase;
import org.apache.http.client.HttpResponseException;

public class AirtableManagerTest extends TestCase {
    private static String apiKey = "key8khS01fFZYRQSv";
    private static String baseName = "appL7E4fvJvvYvyb3";
    private static String tableName = "Recipe";
    private AirtableManager am;
    public void setUp() throws Exception {
        am = new AirtableManager(apiKey, baseName, tableName);
//        am = new AirtableManager(apiKey, baseName, "Practice");
        am.setupAirtable();
        super.setUp();
    }

    public void testGetTable() {
    }

    public void testSetupAirtable() throws AirtableException {

    }

    public void testRetrieveAllList() throws AirtableException, HttpResponseException {
        am.retrieveAllList();
        assertEquals(am.getRecipeList().size(), 2);
        assertEquals(am.getRecipeList().get(0).getName(), "chicken ench");
    }


}