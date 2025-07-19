package com.stepa7.bishop;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/fail")
    public String fail() {
        throw new RuntimeException("Тестовая ошибка");
    }

    @GetMapping("/illegal")
    public String illegal() {
        throw new IllegalArgumentException("Неверный аргумент");
    }
}
