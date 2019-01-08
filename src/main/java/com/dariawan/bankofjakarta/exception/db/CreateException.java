/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
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
