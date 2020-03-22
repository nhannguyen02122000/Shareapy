package com.example.shareapy.utils;

import com.example.shareapy.R;

public class Category {
    private String name;
    private int pictID;

    public Category(String name,int pictID)
    {
        this.name = name;
        this.pictID = pictID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPictID() {
        return pictID;
    }

    public void setPictID(int pictID) {
        this.pictID = pictID;
    }
}
