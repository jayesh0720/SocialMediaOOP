package com.TwitterApp.MajorAssignment.requestBodies;

public class CommentBody {
    private int commentID;
    private String commentBody;
    CommentCreator commentCreator;
    public CommentBody(){}
    public CommentBody(int id,String body,CommentCreator obj){
        this.commentID=id;
        this.commentBody=body;
        this.commentCreator=obj;
    }
    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public CommentCreator getCommentCreator() {
        return commentCreator;
    }

    public void setCommentCreator(CommentCreator commentCreator) {
        this.commentCreator = commentCreator;
    }
}
