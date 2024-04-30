package com.TwitterApp.MajorAssignment.models;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class UserDetailsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    // Getters and setters


    public int getUserID() {
        return ID;
    }

    public void setUserID(int userID) {
        this.ID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public void appendPost(Post p){
        posts.add(p);
    }
}



