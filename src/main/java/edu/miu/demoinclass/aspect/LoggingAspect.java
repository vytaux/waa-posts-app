package edu.miu.demoinclass.aspect;

import edu.miu.demoinclass.model.Logger;
import edu.miu.demoinclass.repository.LoggerRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    private final LoggerRepo loggerRepo;

    public LoggingAspect(LoggerRepo loggerRepo) {
        this.loggerRepo = loggerRepo;
    }

    @Before("@annotation(edu.miu.demoinclass.aspect.LogOperation)")
    public void logBeforeOperation(JoinPoint joinPoint) {
        Logger logger = createLogger(joinPoint);
        loggerRepo.save(logger);
    }

    @AfterReturning(pointcut = "@annotation(edu.miu.demoinclass.aspect.LogOperation)", returning = "result")
    public void logAfterOperation(JoinPoint joinPoint, Object result) {
        // Optionally, log additional information after the operation (if needed)
    }

    private Logger createLogger(JoinPoint joinPoint) {
        Logger logger = new Logger();
        logger.setDatetime(new Date());
        logger.setPrinciple("fakeUser"); // Fake static user
        logger.setOperation(joinPoint.getSignature().toShortString());

        return logger;
    }
}