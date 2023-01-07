package com.luxoft.bankapp.factory;

public enum AccountType {
    CHECKING("checking"),
    SAVING("saving");

    private final String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "accountType='" + accountType + '\'' +
                '}';
    }

    public String getAccountType() {
        return accountType;
    }
}
