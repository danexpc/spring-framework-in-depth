package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {
    }

    @Around("executeLogging()")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        var totalTime = System.currentTimeMillis() - startTime;

        var message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        message.append(" totalTime: ").append(totalTime).append("ms");

        var args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            message.append(" args[ | ");
            Arrays.asList(args).forEach(arg -> message.append(arg).append(" | "));
            message.append("]");
        }

        if (returnValue instanceof Collection) {
            message.append(", returning: ").append(((Collection)returnValue).size()).append(" instance(s)");
        } else {
            message.append(", returning: ").append(returnValue.toString());
        }

        var logMessage = message.toString();
        LOGGER.info(logMessage);

        return returnValue;
    }
}
