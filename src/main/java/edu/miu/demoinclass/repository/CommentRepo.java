package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.model.Comment;
import edu.miu.demoinclass.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> { }