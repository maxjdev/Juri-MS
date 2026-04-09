package com.java.ms_cliente.handler;

import com.java.ms_cliente.handler.response.ApiError;
import com.java.ms_cliente.exceptions.ClienteNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Environment env;

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ApiError> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex, HttpServletRequest request) {
        // Log completa da exceção (inclui stacktrace)
        log.warn("Cliente não encontrado: {}", ex.getMessage(), ex);

        String message = ex.getMessage();
        String trace = null;
        if (isDevProfile()) {
            trace = getStackTrace(ex);
        }

        ApiError body = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                message,
                request.getRequestURI(),
                trace
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleErroGenerico(Exception ex, HttpServletRequest request) {
        // Log completa da exceção (inclui stacktrace)
        log.error("Erro interno inesperado", ex);

        String message = "Erro interno inesperado";
        String trace = null;
        if (isDevProfile()) {
            trace = getStackTrace(ex);
        }

        ApiError body = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                message,
                request.getRequestURI(),
                trace
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private boolean isDevProfile() {
        if (env == null) return false;
        return Arrays.asList(env.getActiveProfiles()).contains("dev");
    }

    private String getStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}