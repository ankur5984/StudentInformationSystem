package com.app.core.StudentInformationSystem.exceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleAllExceptions(Exception _ex, WebRequest _request) {
     //   AppLog.writeLog("BAD REQUEST","error", _ex.getMessage());
        logger.error("Error occurred at class {} with {} HTTP status with error message {}",GlobalExceptionHandler.class,HttpStatus.INTERNAL_SERVER_ERROR,_ex.getMessage());
        return buildErrorResponse(_ex,"BAD REQUEST "+_ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR,_request);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<Object> handleUserNotFoundException(StudentNotFoundException _ex, WebRequest _request) {
       // AppLog.writeLog("NOT FOUND","error", ex.getMessage());
        logger.error("Error occurred at class {} with {} HTTP status with error message {}",GlobalExceptionHandler.class,HttpStatus.BAD_REQUEST,_ex.getMessage());
        return buildErrorResponse(_ex,HttpStatus.NOT_FOUND,_request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      String message,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        ExceptionResponse errorResponse = new ExceptionResponse(new Date(),httpStatus.value(), message);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
