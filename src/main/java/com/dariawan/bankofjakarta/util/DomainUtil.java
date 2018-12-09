package com.dariawan.bankofjakarta.util;

import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;

public class DomainUtil {

    // The accountTypes array stores the valid account types.
    private static String[] accountTypes
            = {"Checking", "Savings", "Credit", "Money Market"};

    public static String[] getAccountTypes() {
        return accountTypes;
    }

    public static void checkAccountType(String type)
            throws IllegalAccountTypeException {
        boolean foundIt = false;

        for (int i = 0; i < accountTypes.length; i++) {
            if (accountTypes[i].equals(type)) {
                foundIt = true;
            }
        }

        if (foundIt == false) {
            throw new IllegalAccountTypeException(type);
        }
    }

    public static boolean isCreditAccount(String type) {
        if (type.equals("Credit")) {
            return true;
        } else {
            return false;
        }
    }
} // DomainUtil
