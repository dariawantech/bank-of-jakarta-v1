/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
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
