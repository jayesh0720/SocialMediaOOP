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
public class CommentController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;



    @PostMapping("/comment")
    public ResponseEntity<?>newComment(@RequestBody NewCommentBody newCommentBody){
        return new UserDetailsService(userRepo,postRepo,commentRepo).newComment(newCommentBody);
    }
    @GetMapping("/comment")
    public ResponseEntity<?>getCommentByID(@RequestParam("commentID") int commentID){
        return new UserDetailsService(userRepo,postRepo,commentRepo).getCommentByID(commentID);
    }
    @PatchMapping("/comment")
    public ResponseEntity<?>editComment(@RequestBody EditCommentRequest editCommentRequest){
        return new UserDetailsService(userRepo,postRepo,commentRepo).editComment(editCommentRequest);
    }
    @DeleteMapping("/comment")
    public ResponseEntity<?>deleteComment(@RequestParam("commentID") int commentID){
        return new UserDetailsService(userRepo,postRepo,commentRepo).deleteComment(commentID);
    }



}
