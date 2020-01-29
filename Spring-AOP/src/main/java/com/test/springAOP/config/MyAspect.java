package com.test.springAOP.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Author: LZJ
 * @Date: 2020/1/29 17:15
 * @Version 1.0
 */
@Aspect //声明一个切面
@Component
public class MyAspect {

    //@Pointcut定义一个切点，切入到哪里 execution最小可以指定到 方法返回参数传入参数
    @Pointcut("execution(* com.test.springAOP.dao.Dao.*(..))")
    public void pointCutExecution(){}

    //within 最小可以指定到类
    @Pointcut("within(com.test.springAOP.dao.*)")
    public void pointCutWithin(){}

    //args 入参为String和Integer的方法会被切入
    @Pointcut("args(java.lang.String,java.lang.Integer)")
    public void pointCutArgs(){}

    //this 如果从spring拿出的对象是目标对象IndexDao 就切入 即使用了CGlib代理的bean
    @Pointcut("this(com.test.springAOP.dao.IndexDao)")
    public void pointCutThis(){}

    //target 如果从spring拿出的对象是IndexDao的代理对象 就切入 即使用了jdk动态代理的bean
    @Pointcut("target(com.test.springAOP.dao.IndexDao)")
    public void pointCutTarget(){}

    //@annotation 有MyAnno的方法 这个注解会被切入
    @Pointcut("@annotation(com.test.springAOP.anno.MyAnno)")
    public void pointCutAtAnnotation(){}

    //@within 有MyAnno的类上 这个注解会被切入
    @Pointcut("@within(com.test.springAOP.anno.MyAnno)")
    public void pointCutAtWithin(){}



    //在连接点方法之前加入该通知
    @Before("pointCutThis()")
    public void before(){
        System.out.println("before");
    }

    //在连接点方法之后加入该通知
    @After("pointCutThis()")
    public void after(){
        System.out.println("after");
    }

    //在连接点方法抛异常后前加入该通知
    @AfterThrowing("pointCutThis()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }

    //在连接点方法之后加入该通知 正常执行完毕后会执行
    @AfterReturning("pointCutThis()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    //在连接点方法之前加入该通知
    @Before("pointCutThis()")
    public void before(JoinPoint joinPoint){
        //joinPoint 可以获取当前切入点的对象
        Object[] args = joinPoint.getArgs();
        System.out.println(args);
    }

    //环绕通知
    @Around("pointCutThis()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //proceedingJoinPoint 用于环绕通知proceedingJoinPoint.proceed()执行对象方法
        System.out.println("start");
        Object retVal = proceedingJoinPoint.proceed();
        System.out.println("end");
        return retVal;
    }
}
