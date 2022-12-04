package com.luxoft.bankapp.domain;

import java.util.List;
import java.util.Objects;

public class Email {
    private Client client;
    private Client from;
    private List<Client> to;
    private String title;
    private String body;

    public Email(Client client, Client from, List<Client> to, String title, String body) {
        this.client = client;
        this.from = from;
        this.to = to;
        this.title = title;
        this.body = body;
    }

    public Email() {

    }

    public Client getClient() {
        return client;
    }

    public Client getFrom() {
        return from;
    }

    public List<Client> getTo() {
        return to;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setFrom(Client from) {
        this.from = from;
    }

    public void setTo(List<Client> to) {
        this.to = to;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Email{" +
                "client=" + client +
                ", from=" + from +
                ", to=" + to +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}
