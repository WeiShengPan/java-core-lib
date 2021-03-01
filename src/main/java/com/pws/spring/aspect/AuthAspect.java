package com.pws.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wilson.pan
 * @date 2021/3/1
 */
@Component
@Aspect
public class AuthAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthAspect.class);

    /**
     * pointcut for all target annotated by Auth
     */
    @Pointcut("@annotation(com.pws.spring.aspect.Auth)")
    public void authPoint() {}

    /**
     * pointcut for some target method
     */
    @Pointcut("execution(* com.pws.spring.aspect.AspectTestService.*(..))")
    public void authPoint2() {}

    /**
     * Around auth
     *
     * @param joinPoint The proceeding join point
     * @param auth      The annotation obj that annotated in target
     * @return
     */
    @Around(value = "authPoint()&&@annotation(auth)")
    public Object aroundAuth(ProceedingJoinPoint joinPoint, Auth auth) {
        Object obj = null;

        try {
            LOGGER.info("AuthAspect aroundAuth before");
            obj = joinPoint.proceed();
            LOGGER.info("AuthAspect aroundAuth after");
        } catch (Throwable throwable) {
            LOGGER.error("AuthAspect aroundAuth error.", throwable);
        }

        return obj;
    }

}
