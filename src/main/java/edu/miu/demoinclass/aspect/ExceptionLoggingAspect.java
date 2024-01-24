package edu.miu.demoinclass.aspect;

import edu.miu.demoinclass.service.ExceptionLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private final ExceptionLogService exceptionLogService;

    public ExceptionLoggingAspect(ExceptionLogService exceptionLogService) {
        this.exceptionLogService = exceptionLogService;
    }

    @AfterThrowing(pointcut = "execution(* edu.miu.demoinclass.controller..*.*(..))", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        exceptionLogService.logException(joinPoint, exception);
    }
}