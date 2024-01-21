package edu.miu.demoinclass.repository;

import edu.miu.demoinclass.model.ExceptionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExceptionLogRepo extends JpaRepository<ExceptionLog, Long> {
    // Additional query methods if needed
}