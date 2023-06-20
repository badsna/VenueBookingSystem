package com.example.emailservice.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Configuration
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around(value = "within(com.example.emailservice.service.EmailSenderService)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("Request for {}.{}() with arguments[s]={}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        Instant startTime = Instant.now();
        Object returnValue = joinPoint.proceed();
        Instant finishTime = Instant.now();
        Long timeElapsed = Duration.between(startTime, finishTime).toMillis();
        logger.info("Response for {}.{} with Result ={}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), returnValue);
        logger.info("Time taken =" + new SimpleDateFormat("mm:ss:SSSS").format(new Date(timeElapsed)));
        return returnValue;

    }



}
