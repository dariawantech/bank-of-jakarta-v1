package com.dariawan.bankofjakarta.exception;

/**
 * This class application exception indicates that a Customer entity has not
 * been found.
 */
public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
