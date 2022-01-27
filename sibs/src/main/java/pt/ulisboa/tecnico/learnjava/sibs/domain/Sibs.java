package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException;
import pt.ulisboa.tecnico.learnjava.bank.services.BankServices;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException;

import java.util.ArrayList;
import java.util.List;


public class Sibs {
	private List<Operation> operations = new ArrayList<>();
	private BankServices bankServices;

	public Sibs(BankServices bankServices) {
		this.bankServices = bankServices;
	}

	public Operation transfer(String sourceIban, String targetIban, int amount)
			throws OperationException, SibsException {
		Operation operation = new Operation(sourceIban, targetIban, amount);
		operations.add(operation);

		if (sourceIban == null || sourceIban.length() == 0 || targetIban == null || targetIban.length() == 0 || amount <= 0) {
			throw new SibsException(SibsException.ErrorType.ILLEGAL_ARGUMENTS);
		}

		try {
			this.bankServices.withdraw(sourceIban, amount);
		} catch (AccountException e) {
			throw new SibsException(SibsException.ErrorType.WITHDRAW);
		}
		try {
			this.bankServices.deposit(targetIban, amount);
		} catch (AccountException e) {
			throw new SibsException(SibsException.ErrorType.DEPOSIT);
		}

		operation.setSucceed(true);

		return operation;
	}

	public List<Operation> getOperations() {
		return operations;
	}
}
