package com.erich.api.exception;

public class BeverageServiceException extends Exception {

    public BeverageServiceException(String message) {
        super(message);
    }

    public BeverageServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
