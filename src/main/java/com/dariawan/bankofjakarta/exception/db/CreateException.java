package com.dariawan.bankofjakarta.exception.db;


/**
 * This db exception indicates that an create/insert failed.
 */
public class CreateException extends Exception {

    public CreateException() {
    }

    public CreateException(String msg) {
        super(msg);
    }
}
