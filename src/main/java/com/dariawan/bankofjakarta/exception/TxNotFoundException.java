package com.dariawan.bankofjakarta.exception;

/**
 * This class application exception indicates that a Tx entity has not been
 * found.
 */
public class TxNotFoundException extends Exception {

    public TxNotFoundException() {
    }

    public TxNotFoundException(String msg) {
        super(msg);
    }
}
