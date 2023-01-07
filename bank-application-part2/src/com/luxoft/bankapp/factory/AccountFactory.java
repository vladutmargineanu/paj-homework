package com.luxoft.bankapp.factory;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;

public class AccountFactory {

    // TODO - Exercise 1 - AccountFactory class
    public static AbstractAccount createNewAccount(AccountType accountType, int id, double amount) {
        if (accountType == null) {
            return null;
        }

        switch (accountType) {
            case CHECKING:
                return new CheckingAccount(id, amount, 0.0);
            case SAVING:
                return new SavingAccount(id, amount);
            default:
                return null;
        }
    }
}
