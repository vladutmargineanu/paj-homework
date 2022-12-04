package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.EmailException;
import com.luxoft.bankapp.service.EmailService;
import com.luxoft.bankapp.utils.ClientRegistrationListener;

import java.text.DateFormat;
import java.util.*;

import static com.luxoft.bankapp.utils.Constants.bankSystem;
import static com.luxoft.bankapp.utils.Constants.bankSystemAdmin;

public class Bank {

    // TODO - Exercise 1 - Task 1 - java.util.Set
    private final Set<Client> clients = new LinkedHashSet<>();

    private final List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    private final EmailService emailService = new EmailService();

    private int printedClients = 0;
    private int emailedClients = 0;
    private int debuggedClients = 0;

    public Bank() {
        listeners.add(new PrintClientListener());
        listeners.add(new EmailNotificationListener());
        listeners.add(new DebugListener());
    }

    public int getPrintedClients() {
        return printedClients;
    }

    public int getEmailedClients() {
        return emailedClients;
    }

    public int getDebuggedClients() {
        return debuggedClients;
    }

    public void addClient(final Client client) throws ClientExistsException {
        if (clients.contains(client)) {
            throw new ClientExistsException("Client already exists into the bank");
        }

        clients.add(client);
        notify(client);
    }

    private void notify(Client client) {
        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    // TODO - Exercise 1 - Task 1 - encapsulate the clients collections - unmodifiableSet()
    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {
            System.out.println("Client added: " + client.getName());
            printedClients++;
        }

    }

    class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {
            System.out.println("Notification email for client " + client.getName() + " to be sent");
            Email email = new Email(client,
                    bankSystem,
                    Collections.singletonList(bankSystemAdmin),
                    "New client added: " + client,
                    "The client " + client + " has been added in system."
            );

            try {
                emailService.sendNotificationEmail(email);
            } catch (EmailException e) {
                System.out.println("Email service is closed, email cannot be sent!");
            }

            emailedClients++;
        }
    }

    public void closeEmailService() {
        emailService.close();
    }

    class DebugListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client client) {
            System.out.println("Client " + client.getName() + " added on: " + DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
            debuggedClients++;
        }
    }

}




