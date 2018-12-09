package com.dariawan.bankofjakarta.exception;

/**
 * This application exception indicates that an Account entity has not been
 * found.
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
