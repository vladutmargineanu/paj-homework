package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.AbstractAccount;
import com.luxoft.bankapp.domain.AccountCache;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import org.junit.Before;
import org.junit.Test;

import static com.luxoft.bankapp.domain.AbstractAccount.CHECKING_ACCOUNT_TYPE;
import static com.luxoft.bankapp.domain.AbstractAccount.SAVING_ACCOUNT_TYPE;
import static com.luxoft.bankapp.factory.AccountType.CHECKING;
import static com.luxoft.bankapp.factory.AccountType.SAVING;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TestAccountCache {

    @Before
    public void setUp() {
        AccountCache.loadCache();
    }

    @Test
    public void createSavingAccountFromCacheTest() {
        AbstractAccount savingAccountFromCache = AccountCache.createAccountFromCache(SAVING);

        assertEquals(SAVING_ACCOUNT_TYPE, savingAccountFromCache.getType());
        assertEquals(0, savingAccountFromCache.getId());
        assertEquals(0, savingAccountFromCache.getBalance(), 0);
        assertThat(savingAccountFromCache, instanceOf(SavingAccount.class));
    }

    @Test
    public void createCheckingAccountFromCacheTest() {
        AbstractAccount checkingAccountFromCache = AccountCache.createAccountFromCache(CHECKING);

        assertEquals(CHECKING_ACCOUNT_TYPE, checkingAccountFromCache.getType());
        assertEquals(0, checkingAccountFromCache.getId());
        assertEquals(0, checkingAccountFromCache.getBalance(), 0);
        assertThat(checkingAccountFromCache, instanceOf(CheckingAccount.class));
    }
}
