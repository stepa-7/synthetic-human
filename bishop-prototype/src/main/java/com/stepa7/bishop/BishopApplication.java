package com.stepa7.bishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.stepa7")
public class BishopApplication {
    public static void main(String[] args) {
        SpringApplication.run(BishopApplication.class, args);
    }
}