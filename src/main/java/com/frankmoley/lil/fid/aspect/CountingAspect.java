package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CountingAspect {

    private static int count;
    private static final Logger LOGGER = LoggerFactory.getLogger(CountingAspect.class);

    @Pointcut("@annotation(Countable)")
    public void executeCounting() {}

    @Before("executeCounting()")
    public void countMethodCall(JoinPoint joinPoint) {
        var message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        message.append(" | counter: ").append(++count);

        var logMessage = message.toString();
        LOGGER.info(logMessage);
    }
}
