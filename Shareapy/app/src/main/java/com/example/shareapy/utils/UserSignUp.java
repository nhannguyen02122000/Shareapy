package com.example.shareapy.utils;

import com.google.firebase.auth.FirebaseAuth;

public class UserSignUp {
    private static UserSignUp instance = null;
    private String userID,password,gender;
    private int age;
    private boolean isReligious,isSpiritual, wasTherapy;
    FirebaseAuth mFireBaseAuth;
    private UserSignUp()
    {
        //Constructor goes here
        mFireBaseAuth = FirebaseAuth.getInstance();
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

    public boolean getReligious(){return this.isReligious;}
    public void setReligious(boolean religious) {this.isReligious = religious;}

    public boolean getSpiritual(){return  this.isSpiritual;}
    public void setSpiritual(boolean spiritual) {this.isSpiritual = spiritual;}

    public boolean getTherapy() {return wasTherapy;}
    public void setTherapy(boolean wasTherapy) {this.wasTherapy = wasTherapy;}
}
