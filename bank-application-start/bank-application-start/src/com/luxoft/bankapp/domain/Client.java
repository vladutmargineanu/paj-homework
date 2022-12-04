package com.luxoft.bankapp.domain;

import java.util.*;

public class Client {
	
	private String name;
	private Gender gender;
	private String city;

	// TODO - Exercise 1 - Task 2 - java.util.Set
	private Set<Account> accounts = new HashSet<>();

	public Client(String name, Gender gender, String city) {
		this.name = name;
		this.gender = gender;
		this.city = city;
	}
	
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}
	
	public Gender getGender() {
		return gender;
	}

	public String getCity() {
		return city;
	}

	// TODO - Exercise 1 - Task 2 - encapsulate the clients collections - unmodifiableSet()
	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}
	
	@Override
	public String toString() {
		return getClientGreeting();
	}

}
