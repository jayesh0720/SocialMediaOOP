package com.TwitterApp.MajorAssignment.requestBodies;

public class UpdatePostRequest {
    private String postBody;
    private int postID;

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
