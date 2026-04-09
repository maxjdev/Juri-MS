package com.java.ms_cliente.handler.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiError(
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        String trace
) {
    public ApiError(int status, String error, String message, String path, String trace) {
        this(LocalDateTime.now(), status, error, message, path, trace);
    }
}