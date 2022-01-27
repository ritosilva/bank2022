package pt.ulisboa.tecnico.learnjava.bank.account

import pt.ulisboa.tecnico.learnjava.bank.domain.Bank
import pt.ulisboa.tecnico.learnjava.bank.domain.CheckingAccount
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException
import pt.ulisboa.tecnico.learnjava.bank.exceptions.BankException

import spock.lang.*

class DepositMethodTest extends Specification {
    private CheckingAccount checking;

    def setup() throws AccountException, BankException {
        Bank bank = new Bank("CGD");

        this.checking = new CheckingAccount(bank, 10);
    }

    def "deposit #amount in CheckingAccount balance is #balance"() throws AccountException {
        when:
        this.checking.deposit(amount);

        then:
        this.checking.getBalance() == balance;

        where:
        amount || balance
        100    || 110
        1      || 11
    }

    def "deposit #amount in CheckingAccount balance is #balance and throw #exception"() {
        when:
        this.checking.deposit(amount);

        then:
        thrown(exception)
        this.checking.getBalance() == balance;

        where:
        amount || balance | exception
        0      || 10      | AccountException
        -100   || 10      | AccountException
    }

    def cleanup() {
        Bank.clearBanks();
    }
}
