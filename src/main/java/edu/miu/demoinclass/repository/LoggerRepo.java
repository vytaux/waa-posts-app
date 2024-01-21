package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.model.Comment;
import edu.miu.demoinclass.model.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepo extends JpaRepository<Logger, Long> { }