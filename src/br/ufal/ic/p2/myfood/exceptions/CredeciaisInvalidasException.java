package br.ufal.ic.p2.myfood.exceptions;

public class CredeciaisInvalidasException extends Exception {
    public CredeciaisInvalidasException() {
        super("Login ou senha invalidos");
    }

    public CredeciaisInvalidasException(String message) {
        super(message);
    }
}