package com.dariawan.bankofjakarta.exception;

/**
 * This an application exception is thrown when an illegal state is
 * detected.
 */
public class IllegalStateException extends Exception {

    public IllegalStateException() {
    }

    public IllegalStateException(String msg) {
        super(msg);
    }
}
