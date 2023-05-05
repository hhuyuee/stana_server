package com.example.demo5;

import com.example.demo5.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * @author huyue87
 * @date 2020-11-15 17:58 2020-11-19 18:35
 * 日志切面类
 */
@Slf4j
@Aspect
@Component
//定义的切面
public class SystemLogAspect {

    //声明AOP切入点，凡是使用了@SystemLog的方法均被拦截
    @Pointcut("@annotation(com.example.demo5.annotation.SystemLog)")
    public void log() {
        System.out.println("这是一个切入点...");
    }

    /**
     * 记录日志 advice
     */
    @After("log()")
    public void afterExec(JoinPoint joinPoint) {
        log.info("日志记录");
//    	joinPoint 的记录
        info(joinPoint);
        try {
            // 获取注解信息
            MethodSignature ms = (MethodSignature) joinPoint.getSignature();
            Method method = ms.getMethod();
            SystemLog log = method.getAnnotation(SystemLog.class);
            if (log != null) {
                //记录操作日志
                System.out.println(log.module()+"******"+log.operationType());
            }
        }  catch (Exception e) {
            log.error("异常信息:{}", e);
        }
    }

    private void info(JoinPoint joinPoint) {
        System.out.println("--------------------------------------------------");
        System.out.println("King:\t" + joinPoint.getKind());
        System.out.println("Target:\t" + joinPoint.getTarget().toString());
        Object[] os = joinPoint.getArgs();
        System.out.println("Args:");
        for (int i = 0; i < os.length; i++) {
            System.out.println("\t==>参数[" + i + "]:\t" + os[i].toString());
        }
        System.out.println("Signature:\t" + joinPoint.getSignature());
        System.out.println("SourceLocation:\t" + joinPoint.getSourceLocation());
        System.out.println("StaticPart:\t" + joinPoint.getStaticPart());
        System.out.println("--------------------------------------------------");
    }
}
