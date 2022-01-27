package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;

public class Operation {
	private final int value;
	private final String targetIban;
	private final String sourceIban;
	private boolean succeed = false;

	public Operation(String sourceIban, String targetIban, int value) throws OperationException {
		this.value = value;
		this.sourceIban = sourceIban;
		this.targetIban = targetIban;
	}

	public String getSourceIban() {
		return this.sourceIban;
	}
	public String getTargetIban() {
		return this.targetIban;
	}
	public int getValue() {
		return this.value;
	}
	public boolean isSucceed() {
		return succeed;
	}
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
}
