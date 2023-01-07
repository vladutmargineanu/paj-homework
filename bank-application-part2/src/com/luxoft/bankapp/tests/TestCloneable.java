package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.factory.AccountFactory;
import com.luxoft.bankapp.factory.AccountType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestCloneable {

    SavingAccount savingAccount;
    CheckingAccount checkingAccount;

    @Before
    public void setUp() {
        savingAccount = (SavingAccount) AccountFactory.createNewAccount(AccountType.SAVING, 1, 100);
        checkingAccount = (CheckingAccount) AccountFactory.createNewAccount(AccountType.CHECKING, 1, 200);
    }

    @Test
    public void savingAccountCloneTest() {
        SavingAccount savingAccountClone = (SavingAccount) savingAccount.clone();

        assertEquals(savingAccount, savingAccountClone);
        assertNotSame(savingAccount, savingAccountClone);  // compare references

    }

    @Test
    public void checkingAccountCloneTest() {
        CheckingAccount checkingAccountClone = (CheckingAccount) checkingAccount.clone();

        assertEquals(checkingAccount, checkingAccountClone);
        assertNotSame(checkingAccount, checkingAccountClone);  // compare references
    }
}
