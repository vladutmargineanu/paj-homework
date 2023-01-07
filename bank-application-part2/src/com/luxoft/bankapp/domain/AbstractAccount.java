package com.luxoft.bankapp.domain;

import java.io.Serializable;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.utils.Params;

public abstract class AbstractAccount implements Account, Serializable, Cloneable {
	
	private static final long serialVersionUID = -2272551373694344386L;
	
	private int id;

	public double balance;
	
	public AbstractAccount(int id, double amount) {
		this.id = id;
		this.balance = amount;
	}

	@Override
	public void deposit(final double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot deposit a negative amount");
		}
		this.balance += amount;
	}

	@Override
	public void withdraw(final double amount) throws NotEnoughFundsException {
		if (amount < 0) {
			throw new IllegalArgumentException("Cannot withdraw a negative amount");
		}
		
		if (amount > maximumAmountToWithdraw()) {
			throw new NotEnoughFundsException(id, balance, amount, "Requested amount exceeds the maximum amount to withdraw");
		}
		
		this.balance -= amount;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public double getBalance() {
		return balance;
	}
	
	@Override
    public long decimalValue(){
        return Math.round(balance);
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractAccount other = (AbstractAccount) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public static Account parse(Params params) {

        switch (params.get("accountType")){
            case "s": return SavingAccount.parse(params);
            case "c": return CheckingAccount.parse(params);
        }

        return null;
    }

	@Override
	public AbstractAccount clone() {
		try {
			AbstractAccount clone = (AbstractAccount) super.clone();
			// TODO: copy mutable state here, so the clone can't change the internals of the original
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
