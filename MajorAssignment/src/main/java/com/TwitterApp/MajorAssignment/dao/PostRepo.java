package com.TwitterApp.MajorAssignment.dao;

import com.TwitterApp.MajorAssignment.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByDateDesc();
    List<Post> findAllByID(int userID);
    Optional<Post> findByID(int ID);
    void deleteByID(int ID);

}
