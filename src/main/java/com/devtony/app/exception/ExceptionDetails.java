package com.devtony.app.exception;

public class ExceptionDetails extends RuntimeException {
    private String userResponse;
    private String severity;

    public ExceptionDetails(String userResponse, String severity){
        super();
        this.userResponse = userResponse;
        this.severity = severity;
    }

    public ExceptionsResponse getResponse(){
        return new ExceptionsResponse(this.userResponse, this.severity);
    }

    public String getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(String userResponse) {
        this.userResponse = userResponse;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
