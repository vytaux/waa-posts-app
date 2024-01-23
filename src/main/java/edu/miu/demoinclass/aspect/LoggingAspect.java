package edu.miu.demoinclass.aspect;

import edu.miu.demoinclass.entity.Logger;
import edu.miu.demoinclass.repository.LoggerRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    private final LoggerRepo loggerRepo;

    public LoggingAspect(LoggerRepo loggerRepo) {
        this.loggerRepo = loggerRepo;
    }

    @Before("execution(* edu.miu.demoinclass.controller..*.*(..))")
    public void logBeforeOperation(JoinPoint joinPoint) {
        Logger logger = createLogger(joinPoint);
        loggerRepo.save(logger);
    }

    private Logger createLogger(JoinPoint joinPoint) {
        Logger logger = new Logger();
        logger.setDatetime(new Date());
        logger.setPrinciple("fakeUser"); // Fake static user
        logger.setOperation(joinPoint.getSignature().toShortString());

        return logger;
    }
}