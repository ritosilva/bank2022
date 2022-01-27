package pt.ulisboa.tecnico.learnjava.bank.services;

import pt.ulisboa.tecnico.learnjava.bank.domain.Account;
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;

public class BankServices {
	public void deposit(String iban, int amount) throws AccountException {
		Account account = Bank.getAccountByIban(iban);

		account.deposit(amount);
	}

	public void withdraw(String iban, int amount) throws AccountException {
		Account account = Bank.getAccountByIban(iban);

		account.withdraw(amount);
	}

}
