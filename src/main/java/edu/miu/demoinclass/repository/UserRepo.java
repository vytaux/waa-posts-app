package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE size(u.posts) > :num")
    List<User> findUsersHavingPostsMoreThan(int num);

    User findByEmail(String email);
}