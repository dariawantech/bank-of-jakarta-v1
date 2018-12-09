package com.dariawan.bankofjakarta.exception;

/**
 * This application exception indicates that the credit line of an account is
 * not large enough to perform an operation.
 */
public class InsufficientCreditException extends Exception {

    public InsufficientCreditException() {
    }

    public InsufficientCreditException(String msg) {
        super(msg);
    }
}
