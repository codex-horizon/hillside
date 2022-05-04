package com.metaverse.hillside.core.helper;

import com.metaverse.hillside.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Slf4j
public class AopRecordHelper {

    public static void doPointcutTask() {
//        log.info("流水号【{}】，在任务之前做", PipelineNumberHolder.get());
    }

    public static void doBeforeTask(JoinPoint point) {
//        log.info("流水号【{}】，在任务之前做", PipelineNumberHolder.get());
    }

    public static void doAfterTask(JoinPoint point) {
//        log.info("流水号【{}】，做后任务", PipelineNumberHolder.get());
    }

    public static void doAfterReturningTask(JoinPoint point, Object retVal) {
//        log.info("流水号【{}】，返回任务后执行", PipelineNumberHolder.get());
    }

    public static void doAfterThrowingTask(JoinPoint point, Exception ex) {
//        log.info("流水号【{}】，投掷任务后做", PipelineNumberHolder.get());
    }

    public static Object doAroundTask(ProceedingJoinPoint joinPoint) throws Throwable {
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
