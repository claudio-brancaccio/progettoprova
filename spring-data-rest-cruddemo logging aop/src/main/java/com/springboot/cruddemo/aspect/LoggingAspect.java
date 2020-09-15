package com.springboot.cruddemo.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.springboot.cruddemo.entity.Employee;
import java.util.List;

@Aspect
@Log4j2
@Configuration
public class LoggingAspect {

    @Pointcut("execution(* com.springboot.cruddemo.*.*.get*(..))")
    private void getter(){}

    @Pointcut("execution(* com.springboot.cruddemo.rest.EmployeeRestController.*(..))")
    private void restMethod(){};

    @Pointcut("restMethod() && !getter()")
    private void notGetter(){};

    @Before("execution(java.util.List<com.springboot.cruddemo.entity.Employee> getEmployees())")
    public void beforeFindAll(JoinPoint joinPoint){
        log.debug("Find All Called");
    }

    @Before("getter()")
    public void beforeGet(JoinPoint joinPoint){
        log.debug("before do stuff");
    }

    @Before("execution(* com.springboot.cruddemo.*.*.get*(*))")
    public void beforeGetByid(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        log.debug(signature.toString());
        Object[] args = joinPoint.getArgs();
        for(Object o: args)
            log.debug(o);

    }

    @AfterReturning(pointcut = "execution(* com.springboot.cruddemo.*.*.get*(*))",
    returning = "result")
    public void afterReturningGetByid(JoinPoint joinPoint, Employee result){
        Signature signature = joinPoint.getSignature();
        log.debug(signature.toString());
        Object[] args = joinPoint.getArgs();
        for(Object o: args)
            log.debug(o);
       // result.setFirstName("NotMimmo");
        log.debug("After" +result);

    }

    @AfterThrowing(pointcut = "execution(* com.springboot.cruddemo.*.*.get*(*))",
            throwing = "ex")
    public void afterThrowingGetByid(JoinPoint joinPoint, Throwable ex){
        Signature signature = joinPoint.getSignature();
        log.debug(signature.toString());
        Object[] args = joinPoint.getArgs();
        for(Object o: args)
            log.debug(o);
        log.debug("Exception " +ex);

    }

    @Before("notGetter()")
    public void beforeNotGet(){
        log.debug("not getter method");
    }

    @Around("execution(* com.springboot.cruddemo.*.*.get*(*))")
    public Object getId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        log.debug("start get method");
        Object result;
        try{
            result = proceedingJoinPoint.proceed();
        }catch (Exception e){
            throw e;
        }
        long durata = System.currentTimeMillis()-begin;
        log.debug("Time spent = "+durata);
        return  result;
    }



}
