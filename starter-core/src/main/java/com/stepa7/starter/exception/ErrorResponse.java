package com.stepa7.starter.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorResponse {
    String error;
    String message;
    String path;
    int status;
    String timestamp;
}