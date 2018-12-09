package com.dariawan.bankofjakarta.exception.db;


/**
 * This db exception indicates that an find a record is failed.
 */
public class FinderException extends Exception {

    public FinderException() {
    }

    public FinderException(String msg) {
        super(msg);
    }
}
