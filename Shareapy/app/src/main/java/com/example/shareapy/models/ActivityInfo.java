package com.example.shareapy.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityInfo {
    private String id;
    private String title;
    private Date time;
    private String url;
    private String counselor;
    private String type;
    private ArrayList<String> registerList;
    private ArrayList<String> bookmarkedList;
    private Map<String, Float> ratingList;
    private Map<String, String> feedbackList;
    public ActivityInfo(String title, Date time, String counselor) {
        ArrayList<String> fakeList = new ArrayList<>();
        ArrayList<String> fakeList2 = new ArrayList<>();
        Map<String, Float> ratingList = new HashMap<>();
        Map<String, String> feedbacList = new HashMap<>();
        fakeList.add("12356");
        fakeList.add("1231rdasdf6");
        fakeList.add("12asdfasdf356");
        fakeList2.add("123agasdfa");
        fakeList2.add("12adsasdfasdf");
        this.title = title;
        this.time = time;
        this.registerList = fakeList;
        this.counselor = counselor;
        this.url = "meet.google.com/guc-cnck-gfu";
        this.bookmarkedList = fakeList2;
        this.ratingList = ratingList;
        this.feedbackList = feedbacList;
    }

    public ActivityInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Map<String, Float> getRatingList() {
        return ratingList;
    }

    public void setRatingList(Map<String, Float> ratingList) {
        this.ratingList = ratingList;
    }

    public Map<String, String> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(Map<String, String> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public static ActivityInfo makeOneSample(){
        return new ActivityInfo("Understanding and Strengthening Family Communication", new Date(), "hello");
    }

    public static ArrayList<ActivityInfo> makeSample(){
        ArrayList<ActivityInfo> listActivity = new ArrayList<>();
        listActivity.add(new ActivityInfo("New Mum's & Mum's-to-be Support Group", new Date(), "Java"));
        listActivity.add(new ActivityInfo("The Art of Grandparenting", new Date(), "Math"));
        listActivity.add(new ActivityInfo("The eyes never lie", new Date(), "Natri"));
        return listActivity;
    }

    @NonNull
    @Override
    public String toString() {
        return id+title+time.toString();
    }
}
