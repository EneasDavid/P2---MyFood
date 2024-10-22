package br.ufal.ic.p2.myfood.exceptions;

public class LimiteEntregaException extends Exception {
    public LimiteEntregaException() {
        super("Indice maior que o esperado");
    }

    public LimiteEntregaException(String message) {
        super(message);
    }
}