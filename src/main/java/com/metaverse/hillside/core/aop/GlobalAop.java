package com.metaverse.hillside.core.aop;

import com.metaverse.hillside.core.helper.AopRecordHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class GlobalAop {

    @Pointcut("execution(* com.metaverse.hillside.work.controller.*Controller.*(..))")
    public void doPointcutTask() {
    }

    @Before("doPointcutTask()")
    public void doBeforeTask(JoinPoint point) {
        AopRecordHelper.doBeforeTask(point);
    }

    @After("doPointcutTask()")
    public void doAfterTask(JoinPoint point) {
        AopRecordHelper.doAfterTask(point);
    }

    @AfterReturning(pointcut = "doPointcutTask()", returning = "retVal")
    public void doAfterReturningTask(JoinPoint point, Object retVal) {
        AopRecordHelper.doAfterReturningTask(point, retVal);
    }

    @AfterThrowing(pointcut = "doPointcutTask()", throwing = "ex")
    public void doAfterThrowingTask(JoinPoint point, Exception ex) {
        AopRecordHelper.doAfterThrowingTask(point, ex);
    }

    @Around("doPointcutTask()")
    public Object doAroundTask(ProceedingJoinPoint joinPoint) throws Throwable {
        return AopRecordHelper.doAroundTask(joinPoint);
    }

}
