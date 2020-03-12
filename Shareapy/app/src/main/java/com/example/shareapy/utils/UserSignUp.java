package com.example.shareapy.utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignUp {
    private static UserSignUp instance = null;
    private String userID,password,gender;
    private int age;
    private String isReligious,isSpiritual, wasTherapy;
    FirebaseAuth mFireBaseAuth;
    private UserSignUp()
    {
        //Constructor goes here
        mFireBaseAuth = FirebaseAuth.getInstance();
        userID=password=gender="";
        age=-1;
        isReligious = isSpiritual = wasTherapy = "";
    }
    public static UserSignUp getInstance()
    {
        if (instance==null)
        {
            instance = new UserSignUp();
        }
        return instance;
    }

    //get set method

    public FirebaseAuth getmFireBaseAuth(){return mFireBaseAuth;}

    public String getUserID(){return this.userID;}
    public void setUserID(String userID){this.userID = userID;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}

    public String getGender(){return this.gender;}
    public void setGender(String gender){this.gender = gender;}

    public int getAge(){return this.age;}
    public void setAge(int age){this.age=age;}

    public String getReligious(){return this.isReligious;}
    public void setReligious(String religious) {this.isReligious = religious;}

    public String getSpiritual(){return  this.isSpiritual;}
    public void setSpiritual(String spiritual) {this.isSpiritual = spiritual;}

    public String getTherapy() {return wasTherapy;}
    public void setTherapy(String wasTherapy) {this.wasTherapy = wasTherapy;}
}
