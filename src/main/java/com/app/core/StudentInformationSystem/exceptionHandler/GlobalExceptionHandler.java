package com.app.core.StudentInformationSystem.exceptionHandler;

import com.app.core.StudentInformationSystem.utils.AppLog;
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



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleAllExceptions(Exception _ex, WebRequest _request) {
        AppLog.writeLog("BAD REQUEST","error", _ex.getMessage());
        return buildErrorResponse(_ex,"BAD REQUEST "+_ex.getMessage(),HttpStatus.BAD_REQUEST,_request);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<Object> handleUserNotFoundException(StudentNotFoundException ex, WebRequest request) {
        AppLog.writeLog("NOT FOUND","error", ex.getMessage());
        return buildErrorResponse(ex,HttpStatus.NOT_FOUND,request);
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
