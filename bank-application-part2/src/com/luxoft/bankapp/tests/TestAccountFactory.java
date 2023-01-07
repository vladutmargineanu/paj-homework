package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.factory.AccountFactory;
import com.luxoft.bankapp.factory.AccountType;
import org.junit.Test;

import static com.luxoft.bankapp.domain.AbstractAccount.CHECKING_ACCOUNT_TYPE;
import static com.luxoft.bankapp.domain.AbstractAccount.SAVING_ACCOUNT_TYPE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestAccountFactory {

    @Test
    public void testDifferentAccounts() {
        AbstractAccount savingAccount1 = AccountFactory.createNewAccount(AccountType.SAVING, 1, 100);
        AbstractAccount savingAccount2 = AccountFactory.createNewAccount(AccountType.SAVING, 1, 100);

        assertEquals(savingAccount1, savingAccount2);
        assertNotSame(savingAccount1, savingAccount2);  // compare references

        AbstractAccount checkingAccount1 = AccountFactory.createNewAccount(AccountType.CHECKING, 1, 200);
        AbstractAccount checkingAccount2 = AccountFactory.createNewAccount(AccountType.CHECKING, 1, 200);

        assertEquals(checkingAccount1, checkingAccount2);
        assertNotSame(checkingAccount1, checkingAccount2);  // compare references
    }

    @Test
    public void testSavingAccount() {
        AbstractAccount savingAccount = AccountFactory.createNewAccount(AccountType.SAVING, 1, 100);

        assertThat(savingAccount, instanceOf(SavingAccount.class));  // same reference
        assertEquals(SAVING_ACCOUNT_TYPE, savingAccount.getType());
        assertEquals(1, savingAccount.getId());
        assertEquals(100, savingAccount.getBalance(), 0.0);
    }

    @Test
    public void testCheckingAccount() {
        AbstractAccount checkingAccount = AccountFactory.createNewAccount(AccountType.CHECKING, 1, 200);

        assertThat(checkingAccount, instanceOf(CheckingAccount.class));  // same reference
        assertEquals(CHECKING_ACCOUNT_TYPE, checkingAccount.getType());
        assertEquals(1, checkingAccount.getId());
        assertEquals(200, checkingAccount.getBalance(), 0.0);
    }
}
