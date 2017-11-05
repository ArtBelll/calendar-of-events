package ru.korbit.ceserver.http;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.korbit.cecommon.exeptions.NotExist;

/**
 * Created by Artur Belogur on 19.10.17.
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Data
    @RequiredArgsConstructor
    private class Error {
        @NonNull private Integer code;
        @NonNull private String message;
    }

    @Data
    @RequiredArgsConstructor
    private class ErrorResponse {
        @NonNull private Error error;
        @NonNull private String status;
    }


    private static HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @ExceptionHandler({NotExist.class})
    protected ResponseEntity<?> notExist(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(
                new Error(400, ex.getMessage()),
                "ERR"
        );

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<?> runtimeException(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(
                new Error(500, ex.getMessage()),
                "ERR"
        );

        return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({Throwable.class})
    protected ResponseEntity<?> unhandledError(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(
                new Error(500, "Unhandled error"),
                "ERR"
        );

        log.error("Unhandled error", ex);

        return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
