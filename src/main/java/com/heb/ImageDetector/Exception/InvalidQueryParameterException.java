package com.heb.ImageDetector.Exception;

public class InvalidQueryParameterException extends Exception{
    public final String message;

    public InvalidQueryParameterException(String message) {
        this.message = message;
    }
}
