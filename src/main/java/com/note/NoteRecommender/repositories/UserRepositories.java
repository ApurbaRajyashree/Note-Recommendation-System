package com.note.NoteRecommender.repositories;

import com.note.NoteRecommender.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositories extends JpaRepository<User,Long> {

    User findByUserEmail(String userEmail);
    User findByUserName(String userName);
}
