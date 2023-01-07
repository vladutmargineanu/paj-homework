package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.AccountCache;
import com.luxoft.bankapp.factory.AccountFactory;
import org.junit.Before;
import org.junit.Test;

import static com.luxoft.bankapp.domain.AbstractAccount.CHECKING_ACCOUNT_TYPE;
import static com.luxoft.bankapp.domain.AbstractAccount.SAVING_ACCOUNT_TYPE;
import static com.luxoft.bankapp.factory.AccountType.CHECKING;
import static com.luxoft.bankapp.factory.AccountType.SAVING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


public class TestAccountCache {

    @Before
    public void setUp() {
        AccountCache.loadCache();
    }

    @Test
    public void createSavingAccountFromCacheTest() {
        AbstractAccount savingAccountFromCache = AccountCache.createAccountFromCache(SAVING);
        AbstractAccount savingAccount = AccountFactory.createNewAccount(SAVING, 0, 0);

        assertEquals(SAVING_ACCOUNT_TYPE, savingAccountFromCache.getType());
        assertEquals(0, savingAccountFromCache.getId());
        assertEquals(0, savingAccountFromCache.getBalance(), 0);

        assertEquals(savingAccountFromCache, savingAccount);
        assertNotSame(savingAccountFromCache, savingAccount);  // compare references
    }

    @Test
    public void createCheckingAccountFromCacheTest() {
        AbstractAccount checkingAccountFromCache = AccountCache.createAccountFromCache(CHECKING);
        AbstractAccount checkingAccount = AccountFactory.createNewAccount(CHECKING, 0, 0);

        assertEquals(CHECKING_ACCOUNT_TYPE, checkingAccountFromCache.getType());
        assertEquals(0, checkingAccountFromCache.getId());
        assertEquals(0, checkingAccountFromCache.getBalance(), 0);

        assertEquals(checkingAccountFromCache, checkingAccount);
        assertNotSame(checkingAccountFromCache, checkingAccount);  // compare references
    }
}
