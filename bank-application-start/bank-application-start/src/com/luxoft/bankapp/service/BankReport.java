package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;

// TODO - Exercise 1 - Task 3, 4, 5 - BankReport class with collections
public class BankReport {

    public static int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public static int getNumberOfAccounts(Bank bank) {
        int totalNumberAccounts = 0;
        for (Client client : bank.getClients()) {
            totalNumberAccounts += client.getAccounts().size();
        }

        return totalNumberAccounts;
    }

    public static SortedSet<Client> getClientsSorted(Bank bank) {
        SortedSet<Client> sortedClients = new TreeSet<>(Comparator.comparing(Client::getName));
        sortedClients.addAll(bank.getClients());
        return sortedClients;
    }

    public static double getTotalSumInAccounts(Bank bank) {
        double totalSumInAccounts = 0;
        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts()) {
                totalSumInAccounts += account.getBalance();
            }
        }

        return totalSumInAccounts;
    }

    public static SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        SortedSet<Account> sortedAccounts = new TreeSet<>(Comparator.comparingDouble(Account::getBalance));
        for (Client client : bank.getClients()) {
            sortedAccounts.addAll(client.getAccounts());
        }

        return sortedAccounts;
    }

    public static double getBankCreditSum(Bank bank) {
        double bankCreditSum = 0;
        for (Client client : bank.getClients()) {
            for (Account account : client.getAccounts())  {
                if (account instanceof CheckingAccount) {
                    if (Double.compare(account.getBalance(), 0) < 0) {
                        bankCreditSum -= account.getBalance();
                    }
                }
            }
        }

        return bankCreditSum;
    }

    public static Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        Map<Client, Collection<Account>> customerAccounts = new HashMap<>();
        for (Client client : bank.getClients()) {
            customerAccounts.put(client, client.getAccounts());
        }

        return customerAccounts;
    }

    public static Map<String, List<Client>> getClientsByCity(Bank bank) {
        // Get all cities sorted
        SortedSet<String> cities = new TreeSet<>();
        for (Client client : bank.getClients()) {
            cities.add(client.getCity());
        }

        // Get all clients for every city
        Map<String, List<Client>> clientsByCity = new TreeMap<>();
        for (String city : cities) {
            List<Client> clientList = new ArrayList<>();
            for (Client client : bank.getClients()) {
                if (client.getCity().equals(city)) {
                    clientList.add(client);
                }
            }
            clientsByCity.put(city, clientList);
        }

        return clientsByCity;
    }
}
