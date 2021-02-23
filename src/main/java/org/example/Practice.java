package org.example;

import com.google.gson.annotations.SerializedName;

public class Practice {
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("bday")
    private int bday;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBday() {
        return bday;
    }

    public void setBday(int bday) {
        this.bday = bday;
    }
}
