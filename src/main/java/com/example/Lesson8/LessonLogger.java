package com.example.Lesson8;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LessonLogger {

    @Pointcut("@within(com.example.Lesson8.Timer) || @annotation(com.example.Lesson8.Timer)")
    public void annotatedWithTimer() {

    }

    @Pointcut("@annotation(com.example.Lesson8.RecoverException)")
    public void annotatedWithRecovery() {

    }

    @Around("annotatedWithTimer()")
    public Object loggableTime(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("target = {}", joinPoint.getTarget().getClass());
        log.info("method = {}", joinPoint.getSignature().getName());
        log.info("args = {}", Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        try {
            Object returnValue =  joinPoint.proceed();
            log.info("Время выполнения - {}", System.currentTimeMillis() - start);
            return returnValue;
        } catch (Throwable e) {
            log.info(e.getMessage());
            throw e;
        }
    }

    @Around("annotatedWithRecovery()")
    public Object recover(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("target = {}", joinPoint.getTarget().getClass());
        log.info("method = {}", joinPoint.getSignature().getName());
        log.info("args = {}", Arrays.toString(joinPoint.getArgs()));

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<? extends RuntimeException> [] nonRecoveryException = method.getAnnotation(RecoverException.class).noRecoverFor();
        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
        } catch (Exception e) {
            for (Class<? extends RuntimeException> aClass : nonRecoveryException) {
                if(!e.getClass().getName().equals(aClass.getName())) {
                    if(method.getReturnType().equals(Integer.class)) {
                        returnValue = 0;
                    }
                    if(method.getReturnType().equals(String.class)) {
                        returnValue = "Proceed";
                    }
                } else throw e;
            }
        }
        return returnValue;
    }
}
