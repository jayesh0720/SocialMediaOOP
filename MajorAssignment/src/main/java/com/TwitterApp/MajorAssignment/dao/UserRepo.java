package com.TwitterApp.MajorAssignment.dao;

import com.TwitterApp.MajorAssignment.models.UserDetailsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserDetailsTable, Long> {
    Optional<UserDetailsTable> findByEmail(String email);
    Optional<UserDetailsTable> findByID(int ID);

}
