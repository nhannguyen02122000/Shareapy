package com.example.shareapy.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityInfo {
    private String title;
    private Date time;
    private float rate;
    private String url;
    private String counselor;
    private ArrayList<String> registerList;
    private ArrayList<String> bookmarkedList;
    public ActivityInfo(String title, Date time, float rate, String counselor) {
        ArrayList<String> fakeList = new ArrayList<>();
        ArrayList<String> fakeList2 = new ArrayList<>();
        fakeList.add("12356");
        fakeList.add("1231rdasdf6");
        fakeList.add("12asdfasdf356");
        fakeList2.add("123agasdfa");
        fakeList2.add("12adsasdfasdf");
        this.title = title;
        this.time = time;
        this.rate = rate;
        this.registerList = fakeList;
        this.counselor = counselor;
        this.url = "meet.google.com/guc-cnck-gfu";
        this.bookmarkedList = fakeList2;
    }

    public ActivityInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }

    public ArrayList<String> getRegisterList() {
        return registerList;
    }

    public void setRegisterList(ArrayList<String> registerList) {
        this.registerList = registerList;
    }

    public ArrayList<String> getBookmarkedList() {
        return bookmarkedList;
    }

    public void setBookmarkedList(ArrayList<String> bookmarkedList) {
        this.bookmarkedList = bookmarkedList;
    }

    public static ActivityInfo makeOneSample(){
        return new ActivityInfo("Understanding and Strengthening Family Communication", new Date(), 4.5f, "hello");
    }

    public static ArrayList<ActivityInfo> makeSample(){
        ArrayList<ActivityInfo> listActivity = new ArrayList<>();
        listActivity.add(new ActivityInfo("New Mum's & Mum's-to-be Support Group", new Date(), 3.5f, "Java"));
        listActivity.add(new ActivityInfo("The Art of Grandparenting", new Date(), 2f, "Math"));
        listActivity.add(new ActivityInfo("The eyes never lie", new Date(), 4.5f, "Natri"));
        return listActivity;
    }

    @NonNull
    @Override
    public String toString() {
        return title+time.toString()+rate;
    }
}
