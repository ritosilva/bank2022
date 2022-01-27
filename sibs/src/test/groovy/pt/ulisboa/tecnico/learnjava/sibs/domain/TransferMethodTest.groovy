package pt.ulisboa.tecnico.learnjava.sibs.domain

import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException
import pt.ulisboa.tecnico.learnjava.bank.services.BankServices
import pt.ulisboa.tecnico.learnjava.sibs.exceptions.SibsException
import spock.lang.*

class TransferMethodTest extends Specification {
	private static final String SOURCE_IBAN = "SourceIban"
	private static final String TARGET_IBAN = "TargetIban"
	private static final int VALUE = 100

	def setup() {
	}

	def void "transfer using mock services and sourceIban #sourceIban, targetIban #targetIban and value #value"() {
		given:
		BankServices bankServices = Mock()
		Sibs sibs = new Sibs(bankServices)
		1 * bankServices.withdraw(sourceIban, value)
		1 * bankServices.deposit(targetIban, value)

		when:
		sibs.transfer(sourceIban, targetIban, value)

		then:
		sibs.getOperations().size() == 1
		Operation operation = sibs.getOperations().get(0)
		operation.getSourceIban() == sourceIban
		operation.getTargetIban() == targetIban
		operation.getValue() == value
		operation.isSucceed() == succeed

		where:
		sourceIban  | targetIban  | value || succeed
		SOURCE_IBAN | TARGET_IBAN | VALUE || true
		SOURCE_IBAN | TARGET_IBAN | 1     || true
	}

	def void "transfer using mocked services and sourceIban #sourceIban, targetIban #targetIban and value #value and throw exception"() {
		given:
		BankServices bankServices = Mock()
		Sibs sibs = new Sibs(bankServices)

		when:
		sibs.transfer(sourceIban, targetIban, value)

		then:
		SibsException sibsException = thrown(SibsException)
		sibsException.getType() == SibsException.ErrorType.ILLEGAL_ARGUMENTS
		and:
		0 * bankServices.withdraw(sourceIban, value)
		0 * bankServices.deposit(targetIban, value)
		and:
		sibs.getOperations().size() == 1
		Operation operation = sibs.getOperations().get(0)
		operation.getSourceIban() == sourceIban
		operation.getTargetIban() == targetIban
		operation.getValue() == value
		operation.isSucceed() == succeed

		where:
		sourceIban  | targetIban  | value  || succeed
		null        | TARGET_IBAN | VALUE  || false
		""          | TARGET_IBAN | VALUE  || false
		SOURCE_IBAN | null        | VALUE  || false
		SOURCE_IBAN | ""          | VALUE  || false
		SOURCE_IBAN | TARGET_IBAN | 0      || false
		SOURCE_IBAN | TARGET_IBAN | -VALUE || false
	}

	def void "transfer using mocked services where withdraw throws AccountException"() {
		given:
		BankServices bankServices = Mock()
		Sibs sibs = new Sibs(bankServices)
		and:
		1 * bankServices.withdraw(SOURCE_IBAN, VALUE) >> { throw new AccountException() }
		0 * bankServices.deposit(TARGET_IBAN, VALUE)

		when:
		sibs.transfer(SOURCE_IBAN, TARGET_IBAN, VALUE)

		then:
		SibsException sibsException = thrown(SibsException)
		sibsException.getType() == SibsException.ErrorType.WITHDRAW
		and:
		sibs.getOperations().size() == 1
		Operation operation = sibs.getOperations().get(0)
		operation.getSourceIban() == SOURCE_IBAN
		operation.getTargetIban() == TARGET_IBAN
		operation.getValue() == VALUE
		operation.isSucceed() == false
	}

	def void "transfer using mocked services where deposit throws AccountException"() {
		given:
		BankServices bankServices = Mock()
		Sibs sibs = new Sibs(bankServices)
		and:
		1 * bankServices.withdraw(SOURCE_IBAN, VALUE)
		1 * bankServices.deposit(TARGET_IBAN, VALUE) >> { throw new AccountException() }

		when:
		sibs.transfer(SOURCE_IBAN, TARGET_IBAN, VALUE)

		then:
		SibsException sibsException = thrown(SibsException)
		sibsException.getType() == SibsException.ErrorType.DEPOSIT
		and:
		sibs.getOperations().size() == 1
		Operation operation = sibs.getOperations().get(0)
		operation.getSourceIban() == SOURCE_IBAN
		operation.getTargetIban() == TARGET_IBAN
		operation.getValue() == VALUE
		operation.isSucceed() == false
	}

	def void cleanup() {
	}

}
