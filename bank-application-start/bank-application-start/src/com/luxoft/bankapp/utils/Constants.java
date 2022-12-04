package com.luxoft.bankapp.utils;

import com.luxoft.bankapp.domain.Client;

public interface Constants {

    String STATISTICS = "-statistics";
    String COMMAND = "display statistic";
    Client bankSystem = new Client("Bank System", null, null);
    Client bankSystemAdmin = new Client("Bank System Admin", null, null);


}
