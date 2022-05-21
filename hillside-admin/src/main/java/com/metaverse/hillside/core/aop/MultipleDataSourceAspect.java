package com.metaverse.hillside.core.aop;

import com.metaverse.hillside.common.constants.SpecifyDataSourceEnum;
import com.metaverse.hillside.core.annotation.SpecifyDataSource;
import com.metaverse.hillside.core.helper.MultipleDataSourceHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
public class MultipleDataSourceAspect {

    @Pointcut("@within(com.metaverse.hillside.core.annotation.SpecifyDataSource)||@annotation(com.metaverse.hillside.core.annotation.SpecifyDataSource)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 获取方法上的注解
        SpecifyDataSource annotationClass = method.getAnnotation(SpecifyDataSource.class);
        if (ObjectUtils.isEmpty(annotationClass)) {
            // 获取类上面的注解
            annotationClass = joinPoint.getTarget().getClass().getAnnotation(SpecifyDataSource.class);
            if (ObjectUtils.isEmpty(annotationClass)) {
                return;
            }
        }
        // 获取注解上的数据源的值的信息
        String dataSourceKey = annotationClass.dataSource();
        if (StringUtils.hasText(dataSourceKey)) {
            // 给当前的执行SQL的操作设置特殊的数据源的信息
            MultipleDataSourceHelper.putDataSource(SpecifyDataSourceEnum.MASTER);
        }
        log.info("AOP动态切换数据源，className={}，methodName={}；dataSourceKey={}",
                joinPoint.getTarget().getClass().getName(),
                method.getName(),
                StringUtils.hasText(dataSourceKey) ? dataSourceKey : "默认数据源");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } finally { // 兼顾事务回滚的情况
            MultipleDataSourceHelper.clearReset(); // 恢复默认
        }
    }

}
