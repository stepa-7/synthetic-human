package com.stepa7.starter.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Command {
    @NotBlank @Size(max = 1000)
    private String description;
    @NotNull
    private Priority priority;
    @NotBlank @Size(max = 100)
    private String author;
    @NotBlank @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?([+-]\\d{2}:\\d{2}|Z)?$",
            message = "Time must be in ISO 8601 format, e.g. 2024-07-18T15:30:00Z"
    )
    private String time;
}