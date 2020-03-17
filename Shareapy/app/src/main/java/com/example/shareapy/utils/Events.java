package com.example.shareapy.utils;

public class Events {
    private String header,date,duration,slot;
    public Events(String header,String date,String duration,String slot)
    {
        this.header=header;
        this.date = date;
        this.duration = duration;
        this.slot=slot;
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
