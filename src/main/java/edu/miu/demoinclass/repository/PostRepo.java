package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByAuthorLike(String like);
}