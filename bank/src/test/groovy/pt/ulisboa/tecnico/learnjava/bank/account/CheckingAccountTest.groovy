package pt.ulisboa.tecnico.learnjava.bank.account

import pt.ulisboa.tecnico.learnjava.bank.domain.Account
import pt.ulisboa.tecnico.learnjava.bank.domain.Bank
import pt.ulisboa.tecnico.learnjava.bank.domain.CheckingAccount
import pt.ulisboa.tecnico.learnjava.bank.exceptions.AccountException

import spock.lang.*

class CheckingAccountTest extends Specification {
    private static final int AMOUNT = 100;

    private Bank bank;

    def setup() {
        this.bank = new Bank("CGD");
    }

    def "create account with null bank"(){
        when:
        Account account = new CheckingAccount(null, AMOUNT);

        then:
        thrown(AccountException)
        this.bank.getTotalNumberOfAccounts() == 0;
    }

    def "create account with positive amount"(){
        when:
        Account account = new CheckingAccount(this.bank, AMOUNT);

        then:
        account.getBalance() == AMOUNT;
        this.bank.getTotalNumberOfAccounts() == 1;
    }

    def "create account with 0 amount"(){
        when:
        Account account = new CheckingAccount(this.bank, 0);

        then:
        account.getBalance() == 0;
        this.bank.getTotalNumberOfAccounts() == 1;
    }

    def "create account with negative amount"(){
        when:
        Account account = new CheckingAccount(this.bank, -AMOUNT);

        then:
        thrown(AccountException)
        this.bank.getTotalNumberOfAccounts() == 0;
    }

    def cleanup() {
        Bank.clearBanks();
    }

}