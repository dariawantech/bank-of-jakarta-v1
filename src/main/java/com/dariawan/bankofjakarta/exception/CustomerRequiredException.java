package com.dariawan.bankofjakarta.exception;

/**
 * This application exception indicates that at least one customer is required
 * for an account.
 */
public class CustomerRequiredException extends Exception {

    public CustomerRequiredException() {
    }

    public CustomerRequiredException(String msg) {
        super(msg);
    }
}
