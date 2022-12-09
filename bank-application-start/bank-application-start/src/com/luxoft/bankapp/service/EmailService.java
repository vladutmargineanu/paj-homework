package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.exceptions.EmailException;
import com.luxoft.bankapp.utils.Queue;

// TODO - Exercise 2 - EmailNotificationListener
public class EmailService implements Runnable {
    private final Queue<Email> emailQueue = new Queue<>();
    /*
     * Thread object - the worker - currentThread
     * The runnable (the job) is the EmailService instance (this)
     */
    private final Thread currentThread;
    private boolean isEmailServiceClosed = false;
    private int noEmails = 0;

    public EmailService() {
        currentThread = new Thread(this);
        currentThread.start();
    }

    public void sendNotificationEmail(Email email) throws EmailException {
        if (!isEmailServiceClosed) {
            emailQueue.add(email);

            synchronized (emailQueue) {
                emailQueue.notify();  // notify waiting threads to resume
            }

        } else {
            throw new EmailException("Email service is closed, mails cannot be sent!");
        }
    }

    public void close() {
        isEmailServiceClosed = true;
        synchronized (emailQueue) {
            emailQueue.notify();  // notify waiting threads to resume
        }

        try {
            currentThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Consuming sent emails
    @Override
    public void run() {
        while(true) {
            // Terminate the thread
            if (isEmailServiceClosed) {
                return;
            }

            Email email = emailQueue.get();
            if (email != null) {
                noEmails++;
                System.out.println("Email successfully sent for client: " + email);
                System.out.println();
            }

            synchronized (emailQueue) {
                try {
                    emailQueue.wait();  // give up lock and wait
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isEmailServiceClosed() {
        return isEmailServiceClosed;
    }

    public int getNoEmails() {
        return noEmails;
    }
}
