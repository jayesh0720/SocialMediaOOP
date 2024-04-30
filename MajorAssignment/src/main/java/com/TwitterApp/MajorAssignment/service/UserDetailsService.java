package com.TwitterApp.MajorAssignment.service;

import com.TwitterApp.MajorAssignment.requestBodies.*;
import com.TwitterApp.MajorAssignment.dao.PostRepo;
import com.TwitterApp.MajorAssignment.dao.CommentRepo;
import com.TwitterApp.MajorAssignment.models.Comment;
import com.TwitterApp.MajorAssignment.models.Post;
import com.TwitterApp.MajorAssignment.models.UserDetailsTable;
import com.TwitterApp.MajorAssignment.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    public UserDetailsService(UserRepo userRepo,PostRepo postRepo,CommentRepo commentRepo){
        this.userRepo=userRepo;
        this.postRepo=postRepo;
        this.commentRepo=commentRepo;
    }


    public ResponseEntity<?> login(@RequestBody LoginReq loginRequest) {
        // Find user by email
        Optional<UserDetailsTable> optionalUser = userRepo.findByEmail(loginRequest.getEmail());

        if(optionalUser.isPresent()) {
            if (optionalUser.get().getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Login Successful");
            }
            else {
                ErrorResponse err=new ErrorResponse("Username/Password Incorrect");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
            }

        }
        else{
            ErrorResponse err=new ErrorResponse("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

        }

    }
    public ResponseEntity<?> signup(@RequestBody SignupReq signupRequest){
        Optional<UserDetailsTable> existingUser=userRepo.findByEmail(signupRequest.getEmail());
        if(existingUser.isPresent()){

            ErrorResponse err=new ErrorResponse("Forbidden, Account already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(err);

        }
        else {
            UserDetailsTable newUser=new UserDetailsTable();
            newUser.setEmail(signupRequest.getEmail());
            newUser.setName(signupRequest.getName());
            newUser.setPassword(signupRequest.getPassword());
            userRepo.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
        }
    }
    public ResponseEntity<?> getUserByID(@RequestParam("userID") int userID){
        Optional<UserDetailsTable> UserInfo=userRepo.findByID(userID);
        if(UserInfo.isPresent()){
            UserDetailsTable user=UserInfo.get();
            GetUserByIdBody responseBody=new GetUserByIdBody();
            responseBody.setEmail(user.getEmail());
            responseBody.setUserID(user.getUserID());
            responseBody.setName(user.getName());
            return ResponseEntity.ok(responseBody);
        }
        else {
            ErrorResponse err=new ErrorResponse("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }

    }

    public ResponseEntity<List<PostObject>> getAllPosts(){
        List<Post> allPosts= postRepo.findAll();
        allPosts.sort(Comparator.comparing(Post::getDate).reversed());
        List<PostObject> postObjects = allPosts.stream()
                .map(post -> {
                    // Retrieve post details
                    int postId = post.getPostID();
                    String postBody = post.getPostBody();
                    Date date = post.getDate();

                    // Retrieve comments for the current post
                    List<Comment> commentsForPost = postRepo.findByID(postId).get().getComments();

                    // Process each comment to create CommentBody objects
                    List<CommentBody> commentBodies = commentsForPost.stream()
                            .map(comment -> {
                                // Retrieve comment details
                                int commentId = comment.getCommentID();
                                String commentBodyText = comment.getCommentBody();

                                // Retrieve comment creator details
                                Optional<Comment> temp = commentRepo.findByID(commentId);
                                Comment userDetails=temp.get();
                                int userId = userDetails.getUser().getUserID();
                                String userName = userDetails.getUser().getName();

                                // Create CommentCreator object
                                CommentCreator commentCreator = new CommentCreator(userId, userName);

                                // Create Comment object
                                CommentBody commentObj = new CommentBody(commentId, commentBodyText, commentCreator);

                                // Create CommentBody object
                                return commentObj;
                            })
                            .collect(Collectors.toList());

                    // Create and return a new PostObject with CommentBody list
                    return new PostObject(postId, postBody, date, commentBodies);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(postObjects);
    }

    public ResponseEntity<?> newPost(@RequestBody PostRequest postRequest){
        Optional<UserDetailsTable> userInfo=userRepo.findByID(postRequest.getUserID());
        if(userInfo.isPresent()){
            Post obj=new Post();
            UserDetailsTable user = userInfo.get();
            obj.setPostBody(postRequest.getPostBody());
            obj.setUser(user);
            postRepo.save(obj);
            user.appendPost(obj);
            userRepo.save(user);
            return ResponseEntity.ok("Post created successfully");
        }
        else{
            ErrorResponse err=new ErrorResponse("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    }

    public ResponseEntity<?> getPostByID(@RequestParam("postID") int postID){
        Optional<Post> getPost=postRepo.findByID(postID);
        if(getPost.isPresent()){
            PostGetRequest p=new PostGetRequest();
            Post temp=getPost.get();
            p.setPostID(temp.getPostID());
            p.setPostBody(temp.getPostBody());
            p.setDate(temp.getDate());
            List<CommentBody> comments = temp.getComments().stream()
                    .map(c -> {
                        CommentBody comm = new CommentBody();
                        comm.setCommentID(c.getCommentID());
                        comm.setCommentBody(c.getCommentBody());
                        CommentCreator temp2 = new CommentCreator();
                        temp2.setUserID(c.getUser().getUserID());
                        temp2.setName(c.getUser().getName());
                        comm.setCommentCreator(temp2);
                        return comm;
                    })
                    .collect(Collectors.toList());

            p.setComments(comments);

            return ResponseEntity.ok(p);

        }
        else {
            ErrorResponse err=new ErrorResponse("Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    }
    public ResponseEntity<?> updatePost(@RequestBody UpdatePostRequest updatePostRequest){
        Optional<Post> getCurrentPost=postRepo.findByID(updatePostRequest.getPostID());
        if(getCurrentPost.isPresent()){
            Post updatedPost=getCurrentPost.get();
            updatedPost.setPostBody(updatePostRequest.getPostBody());
            postRepo.save(updatedPost);
            return ResponseEntity.ok("Post edited successfully");
        }
        else{
            ErrorResponse err=new ErrorResponse("Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

        }

    }
    public ResponseEntity<?> deletePostByID(@RequestParam ("postID") int postID){
        Optional<Post> existingPost=postRepo.findByID(postID);
        if(existingPost.isPresent()){
            postRepo.delete(existingPost.get());

            return ResponseEntity.ok("Post deleted");
        }
        else{
            ErrorResponse err=new ErrorResponse("Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

        }
    }

    public ResponseEntity<?>newComment(@RequestBody NewCommentBody newCommentBody){
        Optional<UserDetailsTable> t1=userRepo.findByID(newCommentBody.getUserID());
        Optional<Post>t2=postRepo.findByID(newCommentBody.getPostID());
        if(t1.isEmpty()) {
            ErrorResponse err=new ErrorResponse("User does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
        if(t2.isEmpty()) {
            ErrorResponse err=new ErrorResponse("Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
        Comment newComm=new Comment();
        UserDetailsTable u=t1.get();
        Post p=t2.get();

        newComm.setCommentBody(newCommentBody.getCommentBody());
        newComm.setUser(u);
        newComm.setPost(p);
        commentRepo.save(newComm);
        p.appendComment(newComm);
        postRepo.save(p);

        return ResponseEntity.ok("Comment created successfully");
    }

    public ResponseEntity<?>getCommentByID(@RequestParam ("commentID") int commentID){
        Optional<Comment> c=commentRepo.findByID(commentID);
        if(c.isPresent()){
            CommentCreator owner=new CommentCreator(c.get().getUser().getUserID(),c.get().getUser().getName());
            CommentBody r=new CommentBody(commentID,c.get().getCommentBody(),owner);
            return ResponseEntity.ok(r);
        }
        else{
            ErrorResponse err=new ErrorResponse("Comment does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

        }
    }

    public ResponseEntity<?>editComment(@RequestBody EditCommentRequest editCommentRequest){
        Optional<Comment> c=commentRepo.findByID(editCommentRequest.getCommentID());
        if(c.isPresent()){
            Comment editedComment=c.get();
            editedComment.setCommentBody(editCommentRequest.getCommentBody());
            commentRepo.save(editedComment);
            return ResponseEntity.ok("Comment edited successfully");
        }
        else {
            ErrorResponse err=new ErrorResponse("Comment does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }

    }
    public ResponseEntity<?>deleteComment(@RequestParam ("commentID") int commentID){
        Optional<Comment> c=commentRepo.findByID(commentID);
        if(c.isPresent()){
            commentRepo.delete(c.get());
            return ResponseEntity.ok("Comment deleted");
        }
        else {
            ErrorResponse err=new ErrorResponse("Comment does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
    }

    public ResponseEntity<?>getAllUsers(){
        List<UserDetailsTable> allUsers=userRepo.findAll();
        List<GetUserByIdBody> resp=allUsers.stream().map(u->{
            GetUserByIdBody t=new GetUserByIdBody();
            t.setName(u.getName());
            t.setUserID(u.getUserID());
            t.setEmail(u.getEmail());
            return t;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

}
