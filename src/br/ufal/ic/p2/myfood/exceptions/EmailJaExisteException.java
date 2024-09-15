package br.ufal.ic.p2.myfood.exceptions;

public class EmailJaExisteException extends RuntimeException{
    public EmailJaExisteException() {
        super("Conta com esse email ja existe");
    }
}