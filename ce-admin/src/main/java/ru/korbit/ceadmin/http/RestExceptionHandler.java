package ru.korbit.ceadmin.http;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.korbit.cecommon.exeptions.BadRequest;
import ru.korbit.cecommon.exeptions.Forbidden;
import ru.korbit.cecommon.exeptions.NotExist;
import ru.korbit.cecommon.exeptions.UnAuthorized;

/**
 * Created by Artur Belogur on 19.10.17.
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Data
    @RequiredArgsConstructor
    private class ErrorResponse {
        @NonNull private int status;
        @NonNull private String message;
    }

    private static HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @ExceptionHandler({UnAuthorized.class})
    protected ResponseEntity<?> handleUnauthorizedRequest(UnAuthorized ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(401,
                "Unauthorized request: " + ex.getMessage());

        return handleExceptionInternal(ex, error, headers, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({Forbidden.class})
    protected ResponseEntity<?> forbidden(Forbidden ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(403, ex.getMessage());

        return handleExceptionInternal(ex, error, headers, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({BadRequest.class})
    protected ResponseEntity<?> badRequest(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(400, ex.getMessage());

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotExist.class})
    protected ResponseEntity<?> notExist(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(400, ex.getMessage());

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RuntimeException.class})
    protected ResponseEntity<?> runtimeException(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        if (ex.getCause() instanceof InvalidDefinitionException) {
            ErrorResponse error = new ErrorResponse(400,
                    "Missing field: " + ex.getCause().getCause().getMessage());

            return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
        }

        ErrorResponse error = new ErrorResponse(500, ex.getMessage());
        log.error(ex.getMessage(), ex);

        return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        ErrorResponse error = new ErrorResponse(status.value(), ex.getMessage());
        log.warn(ex.getMessage());

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Throwable.class})
    protected ResponseEntity<?> unhandledError(Exception ex, WebRequest request) {
        HttpHeaders headers = jsonHeaders();

        ErrorResponse error = new ErrorResponse(500, "Unhandled error");
        log.error("Unhandled error", ex);

        return handleExceptionInternal(ex, error, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
