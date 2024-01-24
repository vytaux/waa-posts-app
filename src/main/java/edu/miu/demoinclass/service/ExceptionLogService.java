package edu.miu.demoinclass.service;

import edu.miu.demoinclass.entity.ExceptionLog;
import edu.miu.demoinclass.repository.ExceptionLogRepo;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ExceptionLogService {

    private final ExceptionLogRepo exceptionLogRepo;

    @Autowired
    public ExceptionLogService(ExceptionLogRepo exceptionLogRepo) {
        this.exceptionLogRepo = exceptionLogRepo;
    }

    public void logException(JoinPoint joinPoint, Exception exception) {
        String operation = joinPoint.getSignature().toShortString();
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setDatetime(new Date());
        exceptionLog.setPrinciple("fakeUser");
        exceptionLog.setOperation(operation);
        exceptionLog.setExceptionType(exception.getClass().getName());

        exceptionLogRepo.save(exceptionLog);
    }
}