package org.example;

import com.sybit.airtable.*;
import com.sybit.airtable.exception.AirtableException;

import java.util.Set;

public class AirtableManager {
    private  String apiKey = "key8khS01fFZYRQSv";
    private  String baseName = "appL7E4fvJvvYvyb3";
    private  String tableName = "Practice";
    private  Airtable airT;
    private  Base base;
    private  Table<Recipe> table;

    AirtableManager(String apikey, String baseName, String tableName) {
        this.apiKey = apikey;
        this.baseName = baseName;
        this.tableName = tableName;
        airT = new Airtable();
    }

    public void setupAirtable() throws AirtableException {
        airT.configure(apiKey);
        base = airT.base(baseName);
        table = base.table(tableName);
    }

//    public Set<Recipe> retrieveFromAirT() {
//
//
//    }







}
