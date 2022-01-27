package pt.ulisboa.tecnico.learnjava.bank.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException;
import pt.ulisboa.tecnico.learnjava.bank.services.BankServices;

public class Bank {
	public enum AccountType {
		CHECKING("CK");

		private final String prefix;

		AccountType(String prefix) {
			this.prefix = prefix;
		}

		public String getPrefix() {
			return this.prefix;
		}
	}

	private static Set<Bank> banks = new HashSet<Bank>();

	public static Bank getBankByCode(String code) {
		return banks.stream().filter(b -> b.getCode().equals(code)).findAny().orElse(null);
	}

	public static void clearBanks() {
		banks.clear();
	}

	private final String code;
	private final Set<Account> accounts;

	public Bank(String code) throws BankException {
		checkCode(code);

		this.code = code;
		this.accounts = new HashSet<Account>();

		banks.add(this);
	}

	private void checkCode(String code) throws BankException {
		// code size is three
		if (code == null || code.length() != 3) {
			throw new BankException();
		}

		// banks have a unique code
		if (getBankByCode(code) != null) {
			throw new BankException();
		}

	}

	public String getCode() {
		return this.code;
	}

	public String createAccount(int amount, int value)
			throws BankException, AccountException {

		Account account = new CheckingAccount(this,amount);

		return getCode() + account.getAccountId();
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public static Account getAccountByIban(String iban) {
		String code = iban.substring(0, 3);
		String accountId = iban.substring(3);

		Bank bank = Bank.getBankByCode(code);
		Account account = bank.getAccountByAccountId(accountId);

		return account;
	}

	public Account getAccountByAccountId(String accountId) {
		return this.accounts.stream().filter(a -> a.getAccountId().equals(accountId)).findFirst().orElse(null);
	}

	public int getTotalNumberOfAccounts() {
		return this.accounts.size();
	}

	public Stream<Account> getAccounts() {
		return this.accounts.stream();
	}

	public int getTotalBalance() {
		return this.accounts.stream().mapToInt(a -> a.getBalance()).sum();
	}

	public static void main(String[] args) throws BankException, AccountException {
		BankServices bankServices = new BankServices();
		Bank cgd = new Bank("CGD");

		cgd.createAccount(100, 0);
		String iban = cgd.createAccount( 1000, 0);

		System.out.println(cgd.getTotalNumberOfAccounts());
		
		Account account = Bank.getAccountByIban(iban);

		try {
			account.deposit(100);
		} catch (AccountException e) {
			System.out.println("You tried to deposit a negative amount of " + e.getValue());
		}

		System.out.println(account.getBalance());
	}

}
