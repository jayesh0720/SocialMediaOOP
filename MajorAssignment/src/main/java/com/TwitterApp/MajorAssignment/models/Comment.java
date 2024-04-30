package com.TwitterApp.MajorAssignment.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne
    @JoinColumn(name = "postID", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserDetailsTable user;

    @Column(nullable = false)
    private String commentBody;

    @Column(nullable = false)
    private LocalDate date;;

    public Comment() {
        this.date = LocalDate.now(); // Set date to current date
    }

    public int getCommentID() {
        return ID;
    }

    public void setCommentID(int commentID) {
        this.ID = commentID;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserDetailsTable getUser() {
        return user;
    }

    public void setUser(UserDetailsTable user) {
        this.user = user;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    // Getters and setters
}


