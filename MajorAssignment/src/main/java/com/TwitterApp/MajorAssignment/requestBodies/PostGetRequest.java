package com.TwitterApp.MajorAssignment.requestBodies;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PostGetRequest
{
    private int postID;
    private String postBody;
    private LocalDate date;
    private List<CommentBody> comments;

    public List<CommentBody> getComments() {
        return comments;
    }

    public void setComments(List<CommentBody> comments) {
        this.comments = comments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.date = localDate;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
}
