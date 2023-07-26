package com.gihae.shop._core.errors;

import com.gihae.shop._core.errors.exception.Exception400;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Aspect
@Component
public class GlobalValidAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    @Before("postMapping()")
    public void validAdvice(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof Errors errors && errors.hasErrors()){
                throw new Exception400(
                        errors.getFieldErrors().get(0).getDefaultMessage() + ":" + errors.getFieldErrors().get(0).getField()
                );
            }
        }
    }
}
