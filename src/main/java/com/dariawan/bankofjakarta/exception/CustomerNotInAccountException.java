package com.dariawan.bankofjakarta.exception;

/**
 * This class application exception indicates that a a customer who was expected
 * to be in an account was not found there.
 */
public class CustomerNotInAccountException extends Exception {

    public CustomerNotInAccountException() {
    }

    public CustomerNotInAccountException(String msg) {
        super(msg);
    }
}
