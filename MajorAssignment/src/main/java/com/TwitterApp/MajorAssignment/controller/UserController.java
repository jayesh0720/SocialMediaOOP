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
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginRequest) {
        // Find user by email
        return new UserDetailsService(userRepo,postRepo,commentRepo).login(loginRequest);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReq signupRequest) {
        // Find user by email
        return new UserDetailsService(userRepo,postRepo,commentRepo).signup(signupRequest);
    }
    @GetMapping("/user")
    public ResponseEntity<?> userDetails(@RequestParam("userID") int userID){
        return new UserDetailsService(userRepo,postRepo,commentRepo).getUserByID(userID);
    }
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return new UserDetailsService(userRepo,postRepo,commentRepo).getAllUsers();
    }


}
