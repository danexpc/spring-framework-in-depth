package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CountingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountingAspect.class);

    private static final Map<String, Integer> countingMap = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    public void executeCounting() {
    }

    @Before("executeCounting()")
    public void countMethodCall(JoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();

        if (countingMap.containsKey(methodName)) {
            countingMap.put(methodName, countingMap.get(methodName) + 1);
        } else {
            countingMap.put(methodName, 1);
        }

        var logMessage = "Method: " + methodName +
                " | counter: " + countingMap.get(methodName);
        LOGGER.info(logMessage);
    }
}
