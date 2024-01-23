package edu.miu.demoinclass.service;

import edu.miu.demoinclass.entity.Logger;
import edu.miu.demoinclass.repository.LoggerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoggerService {

    private final LoggerRepo loggerRepo;

    @Autowired
    public LoggerService(LoggerRepo loggerRepo) {
        this.loggerRepo = loggerRepo;
    }

    public void logOperation(String principle, String operation) {
        Logger logEntry = new Logger();
        logEntry.setDatetime(new Date());
        logEntry.setPrinciple(principle);
        logEntry.setOperation(operation);

        loggerRepo.save(logEntry);
    }
}