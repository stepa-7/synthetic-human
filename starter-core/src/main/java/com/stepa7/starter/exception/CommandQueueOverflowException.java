package com.stepa7.starter.exception;

public class CommandQueueOverflowException extends RuntimeException {
    public CommandQueueOverflowException(String message) {
        super(message);
    }
}