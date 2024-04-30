package com.TwitterApp.MajorAssignment.requestBodies;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostObject {
    private int postID;
    String postBody;
    LocalDate date;
    public PostObject(){}

    private List<CommentBody> comments;
    public PostObject(int id,String body,Date date,List<CommentBody> arr){
        this.postID=id;
        this.postBody=body;
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.date=localDate;
        this.comments=arr;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.date = localDate;
    }

    public List<CommentBody> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentBody> comments) {
        this.comments = comments;
    }
}
