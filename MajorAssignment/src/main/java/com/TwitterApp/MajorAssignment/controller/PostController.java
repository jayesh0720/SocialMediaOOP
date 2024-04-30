package com.TwitterApp.MajorAssignment.controller;

import com.TwitterApp.MajorAssignment.requestBodies.*;
import com.TwitterApp.MajorAssignment.dao.CommentRepo;
import com.TwitterApp.MajorAssignment.dao.PostRepo;
import com.TwitterApp.MajorAssignment.dao.UserRepo;
import com.TwitterApp.MajorAssignment.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    @GetMapping("/")
    public ResponseEntity<List<PostObject>> getAllPosts(){
        return new UserDetailsService(userRepo,postRepo,commentRepo).getAllPosts();
    }
    @PostMapping("/post")
    public ResponseEntity<?> newPost(@RequestBody PostRequest postRequest) {
        // Find user by email
        return new UserDetailsService(userRepo,postRepo,commentRepo).newPost(postRequest);
    }
    @GetMapping("/post")
    public ResponseEntity<?> getPostByID(@RequestParam("postID") int postID){
        return new UserDetailsService(userRepo,postRepo,commentRepo).getPostByID(postID);
    }

    @PatchMapping("/post")
    public ResponseEntity<?>updatePost(@RequestBody UpdatePostRequest updatePostRequest){
        return new UserDetailsService(userRepo,postRepo,commentRepo).updatePost(updatePostRequest);

    }

    @DeleteMapping("/post")
    public ResponseEntity<?>deletePostByID(@RequestParam("postID") int postID){
        return new UserDetailsService(userRepo,postRepo,commentRepo).deletePostByID(postID);
    }





}
