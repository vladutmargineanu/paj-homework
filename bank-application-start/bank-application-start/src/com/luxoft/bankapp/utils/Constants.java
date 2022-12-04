package com.luxoft.bankapp.utils;

import com.luxoft.bankapp.domain.Client;

public interface Constants {

    /*
    * overdraft = a deficit in a bank account caused by
    * drawing more money than the account holds.
    */

    String STATISTICS = "-statistics";
    String COMMAND = "display statistic";
    Client bankSystem = new Client("Bank System", null, null);
    Client bankSystemAdmin = new Client("Bank System Admin", null, null);


}
