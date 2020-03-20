package com.example.shareapy.utils;

import java.util.ArrayList;

public class CategoryActivity {
    String name,date;
    ArrayList<String> registeredList;

    public CategoryActivity(String name, String date)
    {
        this.name=name;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getRegisteredList() {
        return registeredList;
    }

    public void setRegisteredList(ArrayList<String> registeredList) {
        this.registeredList = registeredList;
    }
}
