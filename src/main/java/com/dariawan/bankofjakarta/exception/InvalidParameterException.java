package com.dariawan.bankofjakarta.exception;

/**
 * This an application exception is thrown when an illegal parameter is
 * detected.
 */
public class InvalidParameterException extends Exception {

    public InvalidParameterException() {
    }

    public InvalidParameterException(String msg) {
        super(msg);
    }
}
