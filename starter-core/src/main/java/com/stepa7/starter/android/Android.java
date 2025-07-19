package com.stepa7.starter.android;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class Android {
    private Long id;
    @NotBlank
    private String name;
    private volatile boolean busy;
}
