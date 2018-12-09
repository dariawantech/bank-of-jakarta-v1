package com.dariawan.bankofjakarta.exception;

/**
 * This application exception indicates that an account has insufficient funds
 * to perform an operation.
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String msg) {
        super(msg);
    }
}
