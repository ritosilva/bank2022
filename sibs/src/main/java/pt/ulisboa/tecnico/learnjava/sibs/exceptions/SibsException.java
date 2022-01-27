package pt.ulisboa.tecnico.learnjava.sibs.exceptions;

public class SibsException extends Exception {
    public enum ErrorType  {DEPOSIT, WITHDRAW, ILLEGAL_ARGUMENTS};

    private final ErrorType type;

    public SibsException(ErrorType type) {
       this.type = type;
    }

    public ErrorType getType() {
        return this.type;
    }
}
