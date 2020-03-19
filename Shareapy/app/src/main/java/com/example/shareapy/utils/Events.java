package com.example.shareapy.utils;

import java.util.ArrayList;

public class Events {
    private String header,date,duration,slot,ID;
    private int maxPerson;
    private ArrayList<String> participator;
    public Events(String header,String date,String duration,int maxPerson,String ID,ArrayList<String> participator)
    {
        this.header=header;
        this.date = date;
        this.duration = duration;
        this.slot=slot;
        this.maxPerson = maxPerson;
        this.ID = ID;
        this.participator = participator;
    }

    public ArrayList<String> getParticipator() {
        return participator;
    }

    public void setParticipator(ArrayList<String> participator) {
        this.participator = participator;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
