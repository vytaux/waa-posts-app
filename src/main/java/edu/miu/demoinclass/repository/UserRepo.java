package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.model.Post;
import edu.miu.demoinclass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE size(u.posts) > 1")
    List<User> findUsersHavingMoreThan1Post();
}