package com.TwitterApp.MajorAssignment.dao;

import com.TwitterApp.MajorAssignment.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    // You can add custom query methods here if needed

    Optional<Comment> findByID(int ID);
    List<Comment> findAllByID(int ID);
    void deleteByID(int ID);


}
