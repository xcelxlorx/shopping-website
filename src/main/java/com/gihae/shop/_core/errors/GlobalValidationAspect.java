package com.gihae.shop._core.errors;

import com.gihae.shop._core.errors.exception.Exception400;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j
@Aspect
@Component
public class GlobalValidationAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    @Before("postMapping()")
    public void validationAdvice(JoinPoint jp) {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;

                if (errors.hasErrors()) {
                    throw new Exception400(
                            errors.getFieldErrors().get(0).getDefaultMessage()+":"+errors.getFieldErrors().get(0).getField()
                    );
                }
            }
        }
    }
}
