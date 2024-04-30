package com.TwitterApp.MajorAssignment.requestBodies;

import jakarta.persistence.Embeddable;

@Embeddable
public class CommentCreator {
    private int userID;
    private String name;
    public CommentCreator(){}
    public CommentCreator(int id,String name){
        this.userID=id;
        this.name=name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
