package pt.ulisboa.tecnico.learnjava.bank.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;

public abstract class Account {
	protected static int counter;

	private final String accountId;
	private Bank bank;
	private int balance;
	private boolean inactive;

	public Account(Bank bank) throws AccountException {
		this(bank, 0);
	}

	public Account(Bank bank, int amount) throws AccountException {
		if (bank == null || amount < 0) {
			throw new AccountException();
		}

		this.accountId = getNextAccountId();
		this.bank = bank;
		this.balance = amount;

		this.bank.addAccount(this);
	}

	protected abstract String getNextAccountId();

	public int getBalance() {
		return this.balance;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void deposit(int amount) throws AccountException {
		if (this.inactive) {
			throw new AccountException(amount);
		}

		if (amount <= 0) {
			throw new AccountException(amount);
		}
		this.balance = this.balance + amount;

	}

	public void withdraw(int amount) throws AccountException {
		if (this.inactive) {
			throw new AccountException(amount);
		}

		if (amount <= 0) {
			throw new AccountException();
		}

		this.balance = this.balance - amount;
	}
}
