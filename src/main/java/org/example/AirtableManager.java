package org.example;

import com.sybit.airtable.*;
import com.sybit.airtable.exception.AirtableException;
import org.apache.http.client.HttpResponseException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AirtableManager {
    private String apiKey = "key8khS01fFZYRQSv";
    private String baseName = "appL7E4fvJvvYvyb3";
    private String tableName;
    private Airtable airT;
    private Base base;
    private Table<Rec> table;
    private List<Rec> recipeList;

    public Table<Rec> getTable() {
        return table;
    }

    AirtableManager(String apikey, String baseName, String tableName) {
        this.apiKey = apikey;
        this.baseName = baseName;
        this.tableName = tableName;
        airT = new Airtable();
        recipeList = new ArrayList<>();
    }

    public void setupAirtable() throws AirtableException {
        this.airT.configure(apiKey);
        this.base = airT.base(baseName);
        table = this.base.table(tableName, Rec.class);
    }

    public void retrieveAllList() throws AirtableException, HttpResponseException {
        this.recipeList.addAll(this.table.select());
//         this.recipeList = this.table.select();
        return;
    }

    public Base getBase() {
        return base;
    }

    public List<Rec> getRecipeList() throws AirtableException, HttpResponseException {
        return recipeList;
    }

    public Rec createARecipe(Rec r) throws InvocationTargetException, AirtableException, NoSuchMethodException, IllegalAccessException {
        Rec ret = table.create(r);
        recipeList.add(ret);
        return ret;
    }

    public List<Rec> searchRecipe(final String recipeName) throws AirtableException {
        List<Rec> ret = this.table.select(new Query() {
            @Override
            public String[] getFields() {
                return null;
            }

            @Override
            public Integer getPageSize() {
                return null;
            }

            @Override
            public Integer getMaxRecords() {
                return null;
            }

            @Override
            public String getView() {
                return null;
            }

            @Override
            public List<Sort> getSort() {
                return null;
            }

            @Override
            public String filterByFormula() {
                String s = "{name} = " + "'" + recipeName + "'";
                return s;
            }

            @Override
            public String getOffset() {
                return null;
            }
        });
        return ret;
    }

    public void deleteARecipe(Rec r) throws AirtableException {
        List<Rec> l = searchRecipe(r.getName());

        if(l.size() > 1) {
            System.out.println( l.size() + " recipe with the name" + r.getName() + " cannot delete");
            return;
        }

        if(l.isEmpty()) {
            System.out.println("no such recipe exist");
            return;
        }

        if(recipeList.contains(r)) {
            recipeList.remove(r);
            this.table.destroy(r.getId());
        }

        return;
    }

}
