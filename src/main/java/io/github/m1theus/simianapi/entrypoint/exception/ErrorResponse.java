package io.github.m1theus.simianapi.entrypoint.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorResponse {
    private int statusCode;
    private List<String> errors;

    public ErrorResponse(HttpStatus status, List<String> errors) {
        this.statusCode = status.value();
        this.errors = errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }
}
