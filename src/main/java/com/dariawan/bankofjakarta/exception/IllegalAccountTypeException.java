package com.dariawan.bankofjakarta.exception;

/**
 * This an application exception is thrown when an illegal account type is
 * detected.
 */
public class IllegalAccountTypeException extends Exception {

    public IllegalAccountTypeException() {
    }

    public IllegalAccountTypeException(String msg) {
        super(msg);
    }
}
