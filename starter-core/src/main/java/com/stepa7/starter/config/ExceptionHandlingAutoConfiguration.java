package com.stepa7.starter.config;

import com.stepa7.starter.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@ConditionalOnClass(RestControllerAdvice.class)
public class ExceptionHandlingAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
