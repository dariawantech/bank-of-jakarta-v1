package com.dariawan.bankofjakarta.exception;

/**
 * This application exception indicates that a customer-account relationship
 * already exists. In other words, the customer has already been assigned to the
 * account.
 */
public class CustomerInAccountException extends Exception {

    public CustomerInAccountException() {
    }

    public CustomerInAccountException(String msg) {
        super(msg);
    }
}
