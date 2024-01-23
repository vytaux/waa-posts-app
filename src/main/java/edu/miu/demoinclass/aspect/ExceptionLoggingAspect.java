package edu.miu.demoinclass.aspect;

import edu.miu.demoinclass.entity.ExceptionLog;
import edu.miu.demoinclass.repository.ExceptionLogRepo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private final ExceptionLogRepo exceptionLogRepo;

    public ExceptionLoggingAspect(ExceptionLogRepo exceptionLogRepo) {
        this.exceptionLogRepo = exceptionLogRepo;
    }

    @AfterThrowing(pointcut = "execution(* edu.miu.demoinclass..*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        ExceptionLog exceptionLog = createExceptionLog(joinPoint, exception);
        exceptionLogRepo.save(exceptionLog);
    }

    private ExceptionLog createExceptionLog(JoinPoint joinPoint, Exception exception) {
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setDatetime(new Date());
        exceptionLog.setPrinciple("fakeUser");
        exceptionLog.setExceptionType(exception.getClass().getName());
        exceptionLog.setOperation(joinPoint.getSignature().toShortString());

        return exceptionLog;
    }
}