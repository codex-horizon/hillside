package com.metaverse.hillside.core.aop;

import com.metaverse.hillside.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class RecordLoggerAspect {

    @Pointcut("execution(* com.metaverse.hillside.work.controller.*Controller.*(..))")
    public void doPointcutTask() {
    }

    @Before("doPointcutTask()")
    public void doBeforeTask(JoinPoint point) {
    }

    @After("doPointcutTask()")
    public void doAfterTask(JoinPoint point) {
    }

    @AfterReturning(pointcut = "doPointcutTask()", returning = "retVal")
    public void doAfterReturningTask(JoinPoint point, Object retVal) {
    }

    @AfterThrowing(pointcut = "doPointcutTask()", throwing = "ex")
    public void doAfterThrowingTask(JoinPoint point, Exception ex) {
    }

    @Around("doPointcutTask()")
    public Object doAroundTask(ProceedingJoinPoint joinPoint) throws Throwable {
        String traceId = CommonUtil.getTraceIdByRequest();
        StopWatch stopWatch = new StopWatch(traceId);
        log.info("TraceId=[{}], Around Notification, Start.", traceId);

        stopWatch.start(traceId);
        log.info("TraceId=[{}], Around Notification, Execute Run, Method=[{}], Parameter=[{}].", traceId, joinPoint.getSignature(), Arrays.asList(joinPoint.getArgs()).toString());

        Object proceed = joinPoint.proceed(joinPoint.getArgs());
        log.info("TraceId=[{}], Around Notification, Execute Stop, Method=[{}], Return Value=[{}], Time Consuming=[{}]Millisecond.", traceId, joinPoint.getSignature(), proceed, stopWatch);

        stopWatch.stop();
        log.info("TraceId=[{}], Around Notification, Finish, Processing Time=[{}].", traceId, stopWatch.prettyPrint());

        return proceed;
    }

}
