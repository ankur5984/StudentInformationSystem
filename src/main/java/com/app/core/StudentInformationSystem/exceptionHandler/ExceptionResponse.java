package com.app.core.StudentInformationSystem.exceptionHandler;

import java.util.Date;

public class ExceptionResponse {
    private Date date;
    private int status;
    private String message;

    public ExceptionResponse(Date date, int status, String message) {
        this.date = date;
        this.status = status;
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
