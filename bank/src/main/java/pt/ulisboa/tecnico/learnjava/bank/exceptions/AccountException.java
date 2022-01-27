package pt.ulisboa.tecnico.learnjava.bank.exceptions;

public class AccountException extends Exception {
	private final int value;

	public AccountException() {
		this(0);
	}

	public AccountException(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
