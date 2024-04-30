package com.TwitterApp.MajorAssignment.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserDetailsTable user;

    @Column(nullable = false)
    private String postBody;


    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;


    public Post() {
        this.date = new Date(); // Set date to current date
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPostID() {
        return ID;
    }

    public void setPostID(int postID) {
        this.ID = postID;
    }

    public UserDetailsTable getUser() {
        return user;
    }

    public void setUser(UserDetailsTable user) {
        this.user = user;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void appendComment(Comment c){
        this.comments.add(c);
    }
// Getters and setters
}
