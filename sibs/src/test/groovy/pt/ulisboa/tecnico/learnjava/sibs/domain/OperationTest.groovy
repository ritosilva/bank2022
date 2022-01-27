package pt.ulisboa.tecnico.learnjava.sibs.domain;

import pt.ulisboa.tecnico.learnjava.sibs.domain.Operation;
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.OperationException;

import spock.lang.*;

class OperationTest extends Specification {
	private static final String SOURCE_IBAN = "SourceIban";
	private static final String TARGET_IBAN = "TargetIban";
	private static final int VALUE = 100;

	def "create operation with sourceIban #sourceIban, targetIban #targetIban and value #value"() {
		when:
		Operation operation = new Operation(sourceIban, targetIban, value)

		then:
		operation.getSourceIban() == sourceIban
		operation.getTargetIban() == targetIban
		operation.getValue() == value
		operation.isSucceed() == succeed

		where:
		sourceIban  | targetIban  | value || succeed
		SOURCE_IBAN | TARGET_IBAN | VALUE || false
		null        | TARGET_IBAN | VALUE || false
		""          | TARGET_IBAN | VALUE || false
		SOURCE_IBAN | null        | VALUE || false
		SOURCE_IBAN | ""          | VALUE || false
	}

}
