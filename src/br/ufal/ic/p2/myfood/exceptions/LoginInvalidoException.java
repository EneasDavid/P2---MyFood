package br.ufal.ic.p2.myfood.exceptions;

public class LoginInvalidoException extends RuntimeException{
    public LoginInvalidoException() {
        super("Login ou senha invalidos");
    }
}