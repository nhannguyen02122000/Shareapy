package com.example.shareapy.models;

import java.util.ArrayList;
import java.util.Date;

public class Post {
    private String id;
    private String userID;
    private String content;
    private Date time;
    private boolean isPublic;
    private ArrayList<String> supportList;
    private ArrayList<String> appreciateList;

    public Post() {
        id="";
        content="";
        userID = "";
        time = new Date();
        isPublic=true;
        ArrayList<String> supportList = new ArrayList<>();
        ArrayList<String> appreciateList = new ArrayList<>();
        this.supportList = supportList;
        this.appreciateList = appreciateList;
    }

    public Post(String content){
        this.content = content;
        this.id = "123";
        this.time = new Date();
        this.isPublic = true;
        ArrayList<String> supportList = new ArrayList<>();
        ArrayList<String> appreciateList = new ArrayList<>();
        this.supportList = supportList;
        this.appreciateList = appreciateList;
    }

    public String getUserID() {return userID;}

    public void setUserID(String userID) {this.userID = userID;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ArrayList<String> getSupportList() {
        return supportList;
    }

    public void setSupportList(ArrayList<String> supportList) {
        this.supportList = supportList;
    }

    public ArrayList<String> getAppreciateList() {
        return appreciateList;
    }

    public void setAppreciateList(ArrayList<String> appreciateList) {
        this.appreciateList = appreciateList;
    }

    public static ArrayList<Post> generateFakePost(){
        ArrayList<Post> generatedList = new ArrayList<>();
        generatedList.add(new Post("I have depression"));
        generatedList.add(new Post("I was bullied because of my physical appearance"));
        generatedList.add(new Post("I was recornized by my friend."));
        return generatedList;
    }
}
