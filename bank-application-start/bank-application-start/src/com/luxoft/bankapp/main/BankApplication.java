package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankReport;
import com.luxoft.bankapp.service.BankReportStreams;
import com.luxoft.bankapp.service.BankService;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

import static com.luxoft.bankapp.utils.Constants.*;

public class BankApplication {

    private static Bank bank;

    public static void main(String[] args) {
        bank = new Bank();
        modifyBank();
        printBalance();
        BankService.printMaximumAmountToWithdraw(bank);

        if (args.length > 0 && args[0].equals(STATISTICS)) {
            Scanner commandLine = new Scanner(System.in);
            while (commandLine.hasNext()) {
                String command = commandLine.nextLine();
                if (command.equals(COMMAND)) {
                    displayBankStatistics(bank);
                } else if (command.equals(STOP)) {
                    System.exit(0);
                } else {
                    System.out.println("WRONG COMMAND" + "\n" + "Please, try those commands: " + COMMAND + ", " + STOP + ".");
                }
            }
        }

        displayBankStatistics(bank);

        closeEmail(bank);
    }

    private static void modifyBank() {
        Client client1 = new Client("John", Gender.MALE, "Bucharest");
        Account account1 = new SavingAccount(1, 100);
        Account account2 = new CheckingAccount(2, 100, 20);
        client1.addAccount(account1);
        client1.addAccount(account2);

        Client client2 = new Client("Alexa", Gender.FEMALE, "Paris");
        Account account3 = new SavingAccount(3, 130);
        Account account4 = new CheckingAccount(4, 50, 20);
        client2.addAccount(account3);
        client2.addAccount(account4);

        Client client3 = new Client("Sam", Gender.MALE, "Berlin");
        Account account5 = new SavingAccount(5, 500);
        client3.addAccount(account5);

        Client client4 = new Client("Emma", Gender.FEMALE, "London");
        Account account6 = new SavingAccount(6, 150);
        client4.addAccount(account6);

        try {
            BankService.addClient(bank, client1);
            BankService.addClient(bank, client2);
            BankService.addClient(bank, client3);
            BankService.addClient(bank, client4);
        } catch (ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1.getName());
        }

        account1.deposit(100);
        try {
            account1.withdraw(10);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
        }

        try {
            account2.withdraw(90);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
        }

        try {
            account2.withdraw(100);
        } catch (OverdraftLimitExceededException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
        } catch (NotEnoughFundsException e) {
            System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
        }

        try {
            BankService.addClient(bank, client1);
        } catch (ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1);
        }
    }

    private static void printBalance() {
        System.out.format("%nPrint balance for all clients%n");
        for (Client client : bank.getClients()) {
            System.out.println("Client: " + client);
            for (Account account : client.getAccounts()) {
                System.out.format("Account %d : %.2f%n", account.getId(), account.getBalance());
            }
        }
    }

    private static void displayBankStatistics(Bank bank) {
        BankReportStreams bankReportStreams = new BankReportStreams();

        System.out.println("--- BANK CREDIT STATISTICS ---" + "\n");

        System.out.println("--- NUMBER OF CLIENTS ---");
        System.out.println(BankReport.getNumberOfClients(bank));

        System.out.println("--- NUMBER OF ACCOUNTS ---");
        System.out.println(BankReport.getNumberOfAccounts(bank));

        System.out.println("--- CLIENTS IN ALPHABETICAL ORDER ---");
        System.out.println(BankReport.getClientsSorted(bank));

        System.out.println("--- TOTAL SUM OF MONEY IN ACCOUNTS ---");
        System.out.println(BankReport.getTotalSumInAccounts(bank));

        System.out.println("--- ACCOUNTS SORTED BY THE TOTAL SUM OF MONEY ---");
        for (Account account : BankReport.getAccountsSortedBySum(bank)) {
            System.out.println(account.getId() + " -> " + account.getBalance());
        }

        System.out.println("--- BANK CREDIT SUM ---");
        System.out.println(BankReport.getBankCreditSum(bank));

        System.out.println("--- CUSTOMER ACCOUNT INFO ---");
        for (Map.Entry<Client, Collection<Account>> entry : BankReport.getCustomerAccounts(bank).entrySet()) {
            System.out.println("Client Name: " + entry.getKey().getName() + ", Client City: " + entry.getKey().getCity());
            System.out.println("\tAccounts: ");
            for (Account acc : entry.getValue()) {
                System.out.println("\t\t" + acc.getId() + " : " + acc.getBalance());
            }
        }

        System.out.println("--- CLIENTS ORDERED BY CITY ---");
        System.out.println(BankReport.getClientsByCity(bank));

        System.out.println("\n" + "--- BANK CREDIT STATISTICS WITH STREAMS ---" + "\n");

        System.out.println("--- NUMBER OF CLIENTS WITH STREAMS ---");
        System.out.println(bankReportStreams.getNumberOfClients(bank));

        System.out.println("--- NUMBER OF ACCOUNTS WITH STREAMS ---");
        System.out.println(bankReportStreams.getNumberOfAccounts(bank));

        System.out.println("--- CLIENTS IN ALPHABETICAL ORDER WITH STREAMS ---");
        System.out.println(bankReportStreams.getClientsSorted(bank));

        System.out.println("--- TOTAL SUM OF MONEY IN ACCOUNTS WITH STREAMS ---");
        System.out.println(bankReportStreams.getTotalSumInAccounts(bank));

        System.out.println("--- ACCOUNTS SORTED BY THE TOTAL SUM OF MONEY WITH STREAMS ---");
        for (Account account : bankReportStreams.getAccountsSortedBySum(bank)) {
            System.out.println(account.getId() + " -> " + account.getBalance());
        }

        System.out.println("--- BANK CREDIT SUM WITH STREAMS ---");
        System.out.println(bankReportStreams.getBankCreditSum(bank));

        System.out.println("--- CUSTOMER ACCOUNT INFO WITH STREAMS ---");
        for (Map.Entry<Client, Collection<Account>> entry : bankReportStreams.getCustomerAccounts(bank).entrySet()) {
            System.out.println("Client Name: " + entry.getKey().getName() + ", Client City: " + entry.getKey().getCity());
            System.out.println("\tAccounts: ");
            for (Account acc : entry.getValue()) {
                System.out.println("\t\t" + acc.getId() + " : " + acc.getBalance());
            }
        }

        System.out.println("--- CLIENTS ORDERED BY CITY WITH STREAMS ---");
        System.out.println(bankReportStreams.getClientsByCity(bank));

    }

    public static void closeEmail(Bank bank) {
        bank.closeEmailService();
    }

}
