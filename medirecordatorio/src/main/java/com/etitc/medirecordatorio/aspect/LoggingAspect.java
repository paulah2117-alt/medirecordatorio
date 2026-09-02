package com.etitc.medirecordatorio.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // Intercepta cualquier método de clases que terminen en "Service" o estén dentro del paquete service
    @Pointcut("execution(* com.etitc.medirecordatorio..*Service*.*(..))")
    public void serviciosPointcut() {}

    // Advice Before: Se ejecuta inmediatamente antes de iniciar el método
    @Before("serviciosPointcut()")
    public void registrarInicio(JoinPoint joinPoint) {
        String nombreMetodo = joinPoint.getSignature().getName();
        log.info("==================================================");
        log.info("[AOP LOGGING] -> INICIANDO ejecución del método: {}", nombreMetodo);
        log.info("==================================================");
    }

    // Advice After: Se ejecuta inmediatamente al finalizar el método
    @After("serviciosPointcut()")
    public void registrarFin(JoinPoint joinPoint) {
        String nombreMetodo = joinPoint.getSignature().getName();
        log.info("==================================================");
        log.info("[AOP LOGGING] -> FINALIZÓ la ejecución del método: {}", nombreMetodo);
        log.info("==================================================");
    }
}