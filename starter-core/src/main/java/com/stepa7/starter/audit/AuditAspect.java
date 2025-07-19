package com.stepa7.starter.audit;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    @Value("${audit.mode:console}")
    private String mode;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Around("@annotation(WeylandWatchingYou)")
    public Object audit(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        String info = "Method " + pjp.getSignature() +
                ", Params " + Arrays.toString(pjp.getArgs())
                + ", Result " + result;
        if (mode.equals("console")) {
            logger.info("[AUDIT] " + info);
        } else {
            kafkaTemplate.send("audit", info);
            logger.info("[AUDIT-KAFKA] Sending to kafka...");
        }
        return result;
    }
}
