package com.luxoft.bankapp.service;

import com.beust.ah.A;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.Client;

import java.util.*;
import java.util.stream.Collectors;

// TODO - Exercise 3 - Task 1 - BankReportStreams class using streams
public class BankReportStreams {

    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
        return bank.getClients().stream().
                map(client -> client.getAccounts().size()).
                reduce(0, Integer::sum);
    }

    public SortedSet<Client> getClientsSorted(Bank bank) {
        return bank.getClients().stream().
                collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Client::getName))));
    }

    private static double getClientTotalSum(Client client) {
        return client.getAccounts().stream()
                .map(Account::getBalance)
                .reduce(0.0, Double::sum);
    }
    public double getTotalSumInAccounts(Bank bank) {
        return bank.getClients().stream().
                map(BankReportStreams::getClientTotalSum).
                reduce(0.0, Double::sum);
    }

    public SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        SortedSet<Account> sortedAccountsBySum = new TreeSet<>(Comparator.comparingDouble(Account::getBalance));
        List<Account> result = bank.getClients().stream()
                .map(Client::getAccounts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        sortedAccountsBySum.addAll(result);

        return sortedAccountsBySum;
    }

    public double getBankCreditSum(Bank bank) {
        Double grantedCredits = bank.getClients().stream().
                map(Client::getAccounts).
                flatMap(Collection::stream).
                filter(account -> account instanceof CheckingAccount).
                map(Account::getBalance).
                filter(balance -> Double.compare(balance, 0) < 0).
                reduce(0.0, Double::sum);

        return (-1) * grantedCredits;
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        Map<Client, Collection<Account>> customerAccounts = new HashMap<>();
        bank.getClients().forEach(client -> customerAccounts.put(client, client.getAccounts()));

        return customerAccounts;
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {

        return bank.getClients().stream().
                collect(Collectors.groupingBy(Client::getCity));
    }
}
