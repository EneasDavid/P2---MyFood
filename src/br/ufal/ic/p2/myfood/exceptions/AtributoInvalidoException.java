package br.ufal.ic.p2.myfood.exceptions;

public class AtributoInvalidoException extends Exception {
    public AtributoInvalidoException() {
        super("Atributo invalido");
    }

    public AtributoInvalidoException(String message) {
        super(message);
    }
}